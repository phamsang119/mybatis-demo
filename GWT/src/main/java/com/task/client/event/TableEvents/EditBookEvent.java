package com.task.client.event.TableEvents;


import com.google.gwt.event.shared.GwtEvent;

public class EditBookEvent extends GwtEvent<EditBookEventHandler> {

    public static Type<EditBookEventHandler> TYPE = new Type<>();
    private final long id;

    public EditBookEvent(int id) {
        this.id = id;
    }

    public long getId() { return id; }

    @Override
    public Type<EditBookEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditBookEventHandler editBookEventHandler) {
        editBookEventHandler.onEditBookEvent(this);
    }
}
