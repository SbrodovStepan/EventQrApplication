package com.company.eventqrapplication.view.hall;

import com.company.eventqrapplication.entity.Hall;
import com.company.eventqrapplication.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "halls/:id", layout = MainView.class)
@ViewController(id = "eqa_Hall.detail")
@ViewDescriptor(path = "hall-detail-view.xml")
@EditedEntityContainer("hallDc")
public class HallDetailView extends StandardDetailView<Hall> {
}