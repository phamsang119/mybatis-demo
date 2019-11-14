package metanet.book.controller;

import metanet.book.dto.Book;
import metanet.book.service.BookService;
import org.springframework.http.MediaType;
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
    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    /**
     * Get all books.
     *
     * @return an instance of {@link List<Book>}
     */
    @GetMapping()
    public List<Book> getAll(@RequestParam(defaultValue = "20") int limit, @RequestParam(defaultValue = "1") int page) {
        System.out.println("Limit is " + limit);
        return bookService.getAll(page, limit);
    }

    /**
     * Create a new book.
     *
     * @param request the given book request
     * @return an instance of {@link Book}
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book create(@RequestBody Book request) {
        return bookService.saveOne(request);
    }

    /**
     * Update a book by id
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, path = "/update")
    public Book update(@RequestBody Book request) {
        return bookService.updateOne(request);
    }

    /**
     * Delete a book by id.
     *
     * @param id the given book id
     *
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteOne(id);
    }

    @GetMapping("/cheat")
    public void cheatData() {
        bookService.cheatData();
    }
}
