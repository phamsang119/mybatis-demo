package eureka.book.service;

import eureka.book.dto.Book;
import eureka.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    private static final int NUMBER_OF_DATA = 30000;

    @Override
    public List<Book> getAll(int page, int limit) {
        int offset = (page-1)*limit;
        return bookRepository.getBooks(offset, limit);
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.getBookById(id);
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
            int price =  (int)(Math.random() * ((1000 - 10) + 1)) + 10;
            int rnd = new Random().nextInt(categoryList.length);
            String category = categoryList[rnd];
            Date date = new Date();
            Book book = new Book(bookName, author, description, date, price, category);
            saveOne(book);
        }
    }

    @Override
    public Book saveOne(Book book) {

        bookRepository.insertBook(book);
        return book;
    }

    @Override
    public Book updateOne(Book book) {
        bookRepository.updateBook(book);
        return book;
    }

    @Override
    public void deleteOne(Long id) {
        bookRepository.deleteBook(id);
    }


}
