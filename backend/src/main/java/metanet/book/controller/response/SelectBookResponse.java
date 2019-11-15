package metanet.book.controller.response;

import metanet.book.dto.Book;

import java.util.List;

public class SelectBookResponse {
    private long count;
    private List<Book> books;

    public SelectBookResponse(long count, List<Book> books) {

        this.count = count;
        this.books = books;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
