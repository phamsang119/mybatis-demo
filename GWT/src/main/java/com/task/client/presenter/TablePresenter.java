package com.task.client.presenter;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.task.client.event.MicroserviceEvent.BookRestGWTService;
import com.task.client.event.TableEvents.AddBookEvent;
import com.task.client.event.TableEvents.DeleteBookEvent;
import com.task.client.event.TableEvents.EditBookEvent;
import com.task.server.domain.Book;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class TablePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getAddButton();

        HasClickHandlers getDeleteButton();

        HasClickHandlers getUpdateButton();

        void setData(List<Book> data);

        int getSelectedRowBookId();

        Widget asWidget();

        CellTable<Book> getMainTable();

        String getSelectedBookDescription();

        String getSelectedBookAuthor();
    }

    private final BookRestGWTService bookRestGWTService;
    private final HandlerManager eventBus;
    private final Display display;

    public TablePresenter(BookRestGWTService bookRestGWTService,
                          HandlerManager eventBus,
                          Display view) {
        this.eventBus = eventBus;
        this.display = view;
        this.bookRestGWTService = bookRestGWTService;
    }

    public void bind() {
        display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
        display.getDeleteButton().addClickHandler(event -> eventBus.fireEvent(new DeleteBookEvent(display.getSelectedRowBookId())));
        display.getUpdateButton().addClickHandler(event -> eventBus.fireEvent(new EditBookEvent(display.getSelectedRowBookId())));
        display.getMainTable().addDomHandler(doubleClickEvent -> eventBus.fireEvent(new com.task.client.event.TableEvents.DoubleClickEvent(display.getSelectedBookDescription(), display.getSelectedBookAuthor())),
                com.google.gwt.event.dom.client.DoubleClickEvent.getType());
    }


    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchTable();
    }

    private void fetchTable() {
        bookRestGWTService.list(new MethodCallback<List<Book>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {

            }

            @Override
            public void onSuccess(Method method, List<Book> books) {
                GWT.log(books.toString());
                display.setData(books);
            }
        });
    }
}
