package eureka.book.service;

import eureka.book.dto.Book;


public interface BookService extends GeneralService<Long, Book> {
    void cheatData();
}
