package eureka.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    public final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> list() {
        return bookRepository.findAll();
    }

//    @Override
//    public Book save(Book book) {
//        bookRepository.save(book);
//        return book;
//    }
//
//    @Override
//    public void delete(Long id) {
//        this.bookRepository.deleteById(id);
//    }
//
//    @Override
//    public Book getBook(Long id) {
//        return bookRepository.findById(id).orElseThrow(null);
//    }

}
