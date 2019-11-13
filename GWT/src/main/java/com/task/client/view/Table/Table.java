package com.task.client.view.Table;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.task.client.presenter.TablePresenter;
import com.task.server.domain.Book;

import java.util.List;

public class Table extends Composite implements TablePresenter.Display {

    interface TableUiBinder extends UiBinder<HTMLPanel, Table> {
    }

    private static TableUiBinder ourUiBinder = GWT.create(TableUiBinder.class);
    private ListDataProvider<Book> dataProvider;

    @UiField
    Button addButton;

    @UiField
    Button editButton;

    @UiField
    Button deleteButton;


    @UiField(provided = true)
    CellTable<Book> mainTable;


    public Table() {
        mainTable = new CellTable<>();
        dataProvider = initTable(mainTable);
        initWidget(ourUiBinder.createAndBindUi(this));

    }


    private ListDataProvider<Book> initTable(CellTable<Book> table) {

        ListDataProvider<Book> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(table);

        TextColumn<Book> nameColumn = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getBookName();
            }
        };
        mainTable.addColumn(nameColumn, "Book Name");

        TextColumn<Book> pubDate = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getPublishedDate().toString().substring(0, 10);
            }
        };
        mainTable.addColumn(pubDate, "Published Date");

        TextColumn<Book> price = new TextColumn<Book>() {
            @Override
            public String getValue(Book object) {
                return object.getPrice() + "";
            }
        };
        mainTable.addColumn(price, "Price");

        return dataProvider;
    }


    @Override
    public HasClickHandlers getAddButton() {
        return addButton;
    }

    @Override
    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    @Override
    public HasClickHandlers getUpdateButton() {
        return editButton;
    }

    @Override
    public void setData(List<Book> data) {
        dataProvider.getList().addAll(data);
    }

    @Override
    public CellTable<Book> getMainTable() {
        return mainTable;
    }

    @Override
    public int getSelectedRowBookId() {
        return dataProvider.getList().get(mainTable.getKeyboardSelectedRow()).getId().intValue();
    }

    @Override
    public String getSelectedBookDescription() {
        return dataProvider.getList().get(mainTable.getKeyboardSelectedRow()).getDescription();
    }

    @Override
    public String getSelectedBookAuthor() {
        return dataProvider.getList().get(mainTable.getKeyboardSelectedRow()).getAuthor();
    }
}