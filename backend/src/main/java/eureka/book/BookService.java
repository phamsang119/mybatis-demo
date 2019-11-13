package eureka.book;

import java.util.List;


public interface BookService {
    List<Book> list();

    Book save(Book book);

//    void delete(Long id);

    Book getBook(Long id);


}
