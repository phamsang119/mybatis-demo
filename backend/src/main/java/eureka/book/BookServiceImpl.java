package eureka.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookMapper bookMapper;
    private static final int NUMBER_OF_DATA = 30000;

    @Override
    public List<Book> getAll() {
        return bookMapper.getBooks();
    }

    @Override
    public Book getOne(Long id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public void cheatData() {
        String[] categoryList = new String[]{"Horror", "Comic", "Comedy"};
        for (int i = 0; i < NUMBER_OF_DATA; i++) {
            System.out.println("Adding id " + i);
            String bookName = "Book " + i;
            String author = "Author " + i;
            String description = "No desctiption";
            long price = new Random().nextLong();
            int rnd = new Random().nextInt(categoryList.length);
            String category = categoryList[rnd];
            Date date = new Date();
            Book book = new Book(bookName, author, description, date, price, category);
            saveOne(book);
        }
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
