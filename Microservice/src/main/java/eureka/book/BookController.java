package eureka.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get book by id.
     *
     * @param id the given book id
     * @return an instance of {@link Book}
     */
//    @GetMapping("/{id}")
//    public Book getById(@PathVariable Long id) {
//        return bookService.getBook(id);
//    }

    /**
     * Get all books.
     *
     * @return an instance of {@link List<Book>}
     */
    @GetMapping
    public List<Book> getAll() {
        return bookService.list();
    }

    /**
     * Create a new book.
     *
     * @param request the given book request
     * @return an instance of {@link Book}
     */
//    @PostMapping
//    public Book create(@RequestBody Book request) {
//        return bookService.save(request);
//    }

    /**
     * Delete a book by id.
     *
     * @param id the given book id
//     */
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        bookService.delete(id);
//    }
}
