package com.task.client.presenter;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.task.client.event.EditWindowEvents.CancelEditWindowEvent;
import com.task.client.event.EditWindowEvents.SaveBookEvent;
import com.task.client.event.MicroserviceEvent.BookRestGWTService;
import com.task.server.domain.Book;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Date;

public class EditCardPresenter implements Presenter {

    public interface Display {
        HasClickHandlers getSaveButton();

        HasClickHandlers getCancelButton();

        HasValue<String> getBookName();

        HasValue<String> getAuthor();

        HasValue<String> getDescription();

        HasValue<Date> getDate();

        HasValue<Double> getPrice();

        Widget asWidget();
    }

    private Book book;
    private final BookRestGWTService bookRestGWTService;
    private final HandlerManager eventBus;
    private final Display display;

    public EditCardPresenter(BookRestGWTService bookRestGWTService,
                             HandlerManager eventBus,
                             Display display) {
        this.eventBus = eventBus;
        this.book = new Book();
        this.display = display;
        this.bookRestGWTService = bookRestGWTService;
        bind();
    }

    public EditCardPresenter(BookRestGWTService bookRestGWTService,
                             HandlerManager eventBus,
                             Display display,
                             Long id) {
        this.eventBus = eventBus;
        this.display = display;
        this.bookRestGWTService = bookRestGWTService;
        bind();

        bookRestGWTService.getBook(id, new MethodCallback<Book>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {

            }

            @Override
            public void onSuccess(Method method, Book result) {
                book = result;
                EditCardPresenter.this.display.getBookName().setValue(book.getBookName());
                EditCardPresenter.this.display.getDescription().setValue(book.getDescription());
                EditCardPresenter.this.display.getDate().setValue(book.getPublishedDate());
                EditCardPresenter.this.display.getPrice().setValue(book.getPrice());
                EditCardPresenter.this.display.getAuthor().setValue(book.getAuthor());
            }
        });

    }

    public void bind() {
        this.display.getSaveButton().addClickHandler(event -> doSave());
        this.display.getCancelButton().addClickHandler(event -> eventBus.fireEvent(new CancelEditWindowEvent()));
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
    }

    private void doSave() {
        book.setBookName(display.getBookName().getValue());
        book.setAuthor(display.getAuthor().getValue());
        book.setDescription(display.getDescription().getValue());
        book.setPublishedDate(display.getDate().getValue());
        book.setPrice(display.getPrice().getValue());

        bookRestGWTService.save(book, new MethodCallback<Book>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Error update/add book with id " + book.getId());
            }

            @Override
            public void onSuccess(Method method, Book result) {
                eventBus.fireEvent(new SaveBookEvent(book));
            }
        });
    }
}
