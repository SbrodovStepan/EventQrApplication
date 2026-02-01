package com.company.eventqrapplication.view.eventrequest;

import com.company.eventqrapplication.entity.EventRequest;
import com.company.eventqrapplication.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "event-requests", layout = MainView.class)
@ViewController(id = "eqa_EventRequest.list")
@ViewDescriptor(path = "event-request-list-view.xml")
@LookupComponent("eventRequestsDataGrid")
@DialogMode(width = "64em")
public class EventRequestListView extends StandardListView<EventRequest> {
}