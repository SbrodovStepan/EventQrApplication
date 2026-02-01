package com.company.eventqrapplication.view.hall;

import com.company.eventqrapplication.entity.Hall;
import com.company.eventqrapplication.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "halls", layout = MainView.class)
@ViewController(id = "eqa_Hall.list")
@ViewDescriptor(path = "hall-list-view.xml")
@LookupComponent("hallsDataGrid")
@DialogMode(width = "64em")
public class HallListView extends StandardListView<Hall> {
}