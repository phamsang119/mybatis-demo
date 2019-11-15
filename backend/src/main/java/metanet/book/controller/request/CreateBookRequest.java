package metanet.book.controller.request;

import metanet.book.dto.Book;

import java.util.List;

public class CreateBookRequest {
    private List<Book> listBooks;

    public CreateBookRequest() {
    }

    public List<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(List<Book> listBooks) {
        this.listBooks = listBooks;
    }
}
