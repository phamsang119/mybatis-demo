package com.task.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.task.client.event.DescWindowEvents.CancelDescEvent;
import com.task.client.event.EditWindowEvents.CancelEditWindowEvent;
import com.task.client.event.EditWindowEvents.SaveBookEvent;
import com.task.client.event.MicroserviceEvent.BookRestGWTService;
import com.task.client.event.TableEvents.AddBookEvent;
import com.task.client.event.TableEvents.DeleteBookEvent;
import com.task.client.event.TableEvents.DoubleClickEvent;
import com.task.client.event.TableEvents.EditBookEvent;
import com.task.client.presenter.DescWindowPresenter;
import com.task.client.presenter.EditCardPresenter;
import com.task.client.presenter.Presenter;
import com.task.client.presenter.TablePresenter;
import com.task.client.view.DescriptionWindow.DescWindow;
import com.task.client.view.EditWindow.EditCard;
import com.task.client.view.Table.Table;
import com.task.server.domain.Book;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;


public class AppController implements ValueChangeHandler<String> {

    private final HandlerManager eventBus;
    private final BookRestGWTService bookRestGWTService;
    private HasWidgets container;

    public AppController(BookRestGWTService bookRestGWTService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.bookRestGWTService = bookRestGWTService;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(AddBookEvent.TYPE,
                addBookEvent -> doAddNewContact());

        eventBus.addHandler(EditBookEvent.TYPE,
                editBookEvent -> doEditBook(editBookEvent.getId()));

        eventBus.addHandler(DeleteBookEvent.TYPE, this::doDeleteBook);

        eventBus.addHandler(CancelEditWindowEvent.TYPE, editWindowEvent -> doCancelEvent());
        eventBus.addHandler(SaveBookEvent.TYPE, saveBookEvent -> doSaveBookEvent());

        eventBus.addHandler(DoubleClickEvent.TYPE, this::doShowDescWindow);
        eventBus.addHandler(CancelDescEvent.TYPE, event -> doCancelEvent());
    }

    private void doShowDescWindow(DoubleClickEvent event) {
        History.newItem("description");
        String result = "Description: \n" + event.getDescription() + "\n Author : " + event.getAuthor();
        Presenter presenter = new DescWindowPresenter(result, eventBus, new DescWindow());
        presenter.go(container);
    }

    private void doDeleteBook(DeleteBookEvent event) {
        bookRestGWTService.delete(event.getId(), new MethodCallback<Book>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("cannot delete book with id " + event.getId());
            }

            @Override
            public void onSuccess(Method method, Book book) {

            }
        });

        Presenter presenter = new TablePresenter(bookRestGWTService, eventBus, new Table());
        presenter.go(container);
    }

    private void doAddNewContact() {
        History.newItem("add");
    }

    private void doEditBook(long id) {
        History.newItem("edit", false);
        Presenter presenter = new EditCardPresenter(bookRestGWTService, eventBus, new EditCard(), id);
        presenter.go(container);
    }

    private void doCancelEvent() {
        History.newItem("list");
    }

    private void doSaveBookEvent() {
        History.newItem("list");
    }

    public void go(final HasWidgets container) {
        this.container = container;

        if ("".equals(History.getToken())) {
            History.newItem("list");
        } else {
            History.fireCurrentHistoryState();
        }
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if (token != null) {
            Presenter presenter = null;

            switch (token) {
                case "list":
                    presenter = new TablePresenter(bookRestGWTService, eventBus, new Table());
                    break;
                case "add":
                case "edit":
                    presenter = new EditCardPresenter(bookRestGWTService, eventBus, new EditCard());
                    break;
            }

            if (presenter != null) {
                presenter.go(container);
            }
        }
    }
}
