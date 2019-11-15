package metanet.book.controller.request;

import java.util.List;

public class DeleteBookRequest {
    private List<Long> listId;

    public DeleteBookRequest() {

    }

    public List getListId() {
        return listId;
    }

    public void setListId(List<Long> listId) {
        this.listId = listId;
    }
}
