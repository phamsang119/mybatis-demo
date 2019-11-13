package eureka.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getAll() {
        return bookMapper.getBooks();
    }

    @Override
    public Book getOne(Long id) {
        return bookMapper.getBookById(id);
    }
    @Override
    public Book saveOne(Book book) {

        bookMapper.insertBook(book);
        return book;
    }

    @Override
    public Book updateOne(Book book) {
        bookMapper.updateBook(book);
        return book;
    }

    @Override
    public void deleteOne(Long id) {
        bookMapper.deleteBook(id);
    }



}
