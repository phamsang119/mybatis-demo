package com.task.client.presenter;

import com.github.gwtbootstrap.client.ui.TextArea;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.task.client.event.DescWindowEvents.CancelDescEvent;


public class DescWindowPresenter implements Presenter {

    public interface Display {
        TextArea getTextArea();

        HasClickHandlers getCancelButton();

        Widget asWidget();
    }

    private final String description;
    private final HandlerManager eventBus;
    private final DescWindowPresenter.Display display;

    public DescWindowPresenter(String description,
                               HandlerManager eventBus,
                               DescWindowPresenter.Display view) {
        this.description = description;
        this.eventBus = eventBus;
        this.display = view;
    }

    public void bind() {
        display.getCancelButton().addClickHandler(clickEvent -> eventBus.fireEvent(new CancelDescEvent()));
    }


    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        initTextBox();
    }

    private void initTextBox() {
        display.getTextArea().setText(description);
    }

}
