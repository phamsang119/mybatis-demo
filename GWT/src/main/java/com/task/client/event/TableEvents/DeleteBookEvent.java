package com.task.client.event.TableEvents;


import com.google.gwt.event.shared.GwtEvent;

public class DeleteBookEvent extends GwtEvent<DeleteBookEventHandler>{

    public static Type<DeleteBookEventHandler> TYPE = new Type<>();

    private final long id;

    public DeleteBookEvent(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public Type<DeleteBookEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DeleteBookEventHandler deleteBookEventHandler) {
        deleteBookEventHandler.onDeleteBookEvent(this);
    }
}
