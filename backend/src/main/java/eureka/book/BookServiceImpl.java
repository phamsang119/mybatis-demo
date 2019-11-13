package eureka.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.selectAll()   ;
    }

    @Override
    public Book save(Book book) {
        bookMapper.insertBook(book);
        return book;
    }
//
//    @Override
//    public void delete(Long id) {
//        this.bookRepository.deleteById(id);
//    }

    @Override
    public Book getBook(Long id) {
        return bookMapper.selectOne(id);
    }

}
