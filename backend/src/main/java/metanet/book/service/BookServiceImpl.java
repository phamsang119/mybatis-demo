package metanet.book.service;

import metanet.book.dto.Book;
import metanet.book.model.BookEntity;
import metanet.book.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    private static final int NUMBER_OF_DATA = 30000;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Book> getAll(int page, int limit) {
        int offset = (page - 1) * limit;
        List<BookEntity> books = bookRepository.getBooks(offset, limit);
        return books.stream()
                .map(bookEntity -> modelMapper.map(bookEntity, Book.class))
                .collect(Collectors.toList());
    }

    @Override
    public Book saveOne(Book object) {
        BookEntity bookEntity = modelMapper.map(object, BookEntity.class);
        System.out.println(bookEntity);
        bookRepository.insertBook(bookEntity);
        return object;
    }

    @Override
    public void saveMany(List<Book> list) {
        List<BookEntity> listBooks = list.stream().map(book -> modelMapper.map(book, BookEntity.class)).collect(Collectors.toList());
        bookRepository.insertBooks(listBooks);
    }

    @Override
    public Book getOne(Long id) {
        return modelMapper.map(bookRepository.getBookById(id), Book.class);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    /**
     * Purpose for generate data
     */
    @Override
    public void cheatData() {
        String[] categoryList = new String[]{"Horror", "Comic", "Comedy"};
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            System.out.println("Adding id " + i);
            String bookName = "Book " + i;
            String author = "Author " + i;
            String description = "No description";
            int price = (int) (Math.random() * ((1000 - 10) + 1)) + 10;
            int rnd = new Random().nextInt(categoryList.length);
            String category = categoryList[rnd];
            Date date = new Date();
            Book book = new Book(bookName, author, description, date, price, category);
            saveOne(book);
        }
    }

    @Override
    public Book updateOne(Book book) {
        bookRepository.updateBook(modelMapper.map(book, BookEntity.class));
        return book;

    }

    @Override
    public void updateMany(List<Book> list) {
        List<BookEntity> listBooks = list.stream().map(book -> modelMapper.map(book, BookEntity.class)).collect(Collectors.toList());
        bookRepository.updateBooks(listBooks);
    }

    @Override
    public void deleteOne(Long id) {
        bookRepository.deleteBook(id);
    }

    @Override
    public void deleteMany(List listId) {
        bookRepository.deleteBooks(listId);
    }


}
