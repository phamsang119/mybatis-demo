package com.task.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import com.task.client.event.MicroserviceEvent.BookRestGWTService;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

/**
 * Entry point
 */
public class App implements EntryPoint {

    @Override
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8761");
        Resource resource = new Resource(Defaults.getServiceRoot());
        BookRestGWTService bookRestGWTService = GWT.create(BookRestGWTService.class);
        ((RestServiceProxy) bookRestGWTService).setResource(resource);;

        HandlerManager eventBus = new HandlerManager(null);
        AppController appViewer = new AppController(bookRestGWTService, eventBus);
        appViewer.go(RootPanel.get());
    }

}
