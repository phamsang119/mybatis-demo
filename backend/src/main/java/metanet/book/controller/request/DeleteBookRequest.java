package metanet.book.controller.request;

import java.util.List;

public class DeleteBookRequest {
    private List listId;

    public DeleteBookRequest() {

    }

    public List getListId() {
        return listId;
    }

    public void setListId(List listId) {
        this.listId = listId;
    }
}
