package eureka.book;

import java.util.List;


public interface BookService {
    List<Book> getAll();

    Book saveOne(Book book);

    Book updateOne(Book book);

    void deleteOne(Long id);

    Book getOne(Long id);


}
