package metanet.book.controller.response;

import metanet.book.dto.Book;

import java.util.List;

public class SelectBookResponse {
    private Paging paging;
    private List<Book> books;

    public SelectBookResponse(Paging paging, List<Book> books) {

        this.paging = paging;
        this.books = books;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
