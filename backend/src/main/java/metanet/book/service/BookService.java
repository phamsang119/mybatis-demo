package metanet.book.service;

import metanet.book.dto.Book;


public interface BookService extends GeneralService<Long, Book> {
    void cheatData();
}
