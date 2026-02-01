package com.company.eventqrapplication.view.eventrequest;

import com.company.eventqrapplication.entity.EventParticipant;
import com.company.eventqrapplication.entity.EventRequest;
import com.company.eventqrapplication.entity.User;
import com.company.eventqrapplication.service.EventCodeGenerator;
import com.company.eventqrapplication.service.EventEmailService;
import com.company.eventqrapplication.service.EventQrCodeService;
import com.company.eventqrapplication.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Route(value = "event-requests/:id", layout = MainView.class)
@ViewController(id = "eqa_EventRequest.detail")
@ViewDescriptor(path = "event-request-detail-view.xml")
@EditedEntityContainer("eventRequestDc")
public class EventRequestDetailView extends StandardDetailView<EventRequest> {

    @Autowired
    private EventQrCodeService qrCodeService;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private EventCodeGenerator eventCodeGenerator;

    private List<UserSelection> userSelections;

    @ViewComponent
    private Grid<UserSelection> usersGrid;

    @Autowired
    private Notifications notifications;

    @Autowired
    private EventEmailService emailService;

    @Subscribe
    public void onInitEntity(InitEntityEvent<EventRequest> event) {
        EventRequest req = event.getEntity();

        req.setRequestDate(LocalDate.now());
        req.setNumber(dataManager.loadValue(
                "select coalesce(max(e.number), 0) + 1 from eqa_EventRequest e",
                Integer.class
        ).one());

        req.setEventCode(eventCodeGenerator.generateEventCode(req));
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        initUsersGrid();
        loadUsers();
    }

    private void initUsersGrid() {
        // Удаляем старые колонки, если были
        usersGrid.removeAllColumns();

        usersGrid.addColumn(UserSelection::getLastName)
                .setHeader("Фамилия")
                .setAutoWidth(true);

        usersGrid.addColumn(UserSelection::getFirstName)
                .setHeader("Имя")
                .setAutoWidth(true);

        usersGrid.addColumn(UserSelection::getMiddleName)
                .setHeader("Отчество")
                .setAutoWidth(true);

        usersGrid.addColumn(UserSelection::getDepartment)
                .setHeader("Организация")
                .setAutoWidth(true);

        // Колонка с галочкой
        usersGrid.addComponentColumn(us -> {
                    Checkbox cb =
                            new Checkbox();

                    cb.setValue(us.isSelected());

                    cb.addValueChangeListener(e ->
                            us.setSelected(e.getValue())
                    );

                    return cb;
                })
                .setHeader("Участник")
                .setAutoWidth(true);
    }

    private void loadUsers() {
        EventRequest req = getEditedEntity();

        List<User> allUsers = dataManager.load(User.class)
                .all()
                .list();

        List<EventParticipant> participants = req.getParticipants() == null
                ? List.of()
                : req.getParticipants();

        userSelections = allUsers.stream()
                .map(u -> {
                    EventParticipant ep = participants.stream()
                            .filter(p -> p.getUser().equals(u))
                            .findFirst()
                            .orElse(null);

                    UserSelection us = new UserSelection(u, ep != null);
                    if (ep != null) {
                        us.setQrCode(ep.getQrCode());
                    }
                    return us;
                })
                .toList();

        usersGrid.setItems(userSelections);
    }

    @Subscribe
    public void onBeforeSave(BeforeSaveEvent event) {
        EventRequest req = getEditedEntity();

        if (req.getParticipants() == null) {
            req.setParticipants(new ArrayList<>());
        } else {
            req.getParticipants().clear();
        }

        for (UserSelection us : userSelections) {
            if (us.isSelected()) {
                EventParticipant ep = dataManager.create(EventParticipant.class);
                ep.setUser(us.getUser());
                ep.setEventRequest(req);

                byte[] qr = qrCodeService.generateForParticipant(req, us.getUser());

                ep.setQrCode(qr);
                us.setQrCode(qr);

                req.getParticipants().add(ep);
            }
        }

        if (req.getParticipants() != null) {
            for (EventParticipant participant : req.getParticipants()) {
                emailService.sendEmailWithQr(participant);
            }
        }

        usersGrid.getDataProvider().refreshAll();

    }

    @Subscribe(id = "openQr", subject = "clickListener")
    public void onOpenQrClick(final ClickEvent<JmixButton> event) {
        UserSelection selected = usersGrid.asSingleSelect().getValue();

        if (selected == null) {
            notifications.create("Выберите участника")
                    .withType(Notifications.Type.WARNING)
                    .show();
            return;
        }

        if (selected.getQrCode() == null) {
            notifications.create("QR-код для этого участника ещё не создан")
                    .withType(Notifications.Type.WARNING)
                    .show();
            return;
        }

        showQrDialog(selected);

    }

    private void showQrDialog(UserSelection userSelection) {

        StreamResource resource = new StreamResource(
                "qr.png",
                () -> new ByteArrayInputStream(userSelection.getQrCode())
        );

        Image image = new Image(resource, "QR-код");
        image.setWidth("250px");
        image.setHeight("250px");

        VerticalLayout layout = new VerticalLayout(
                new Span(userSelection.getLastName() + " " +
                        userSelection.getFirstName()),
                image
        );
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setPadding(true);

        Dialog dialog = new Dialog();
        dialog.add(layout);
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.open();
    }
}