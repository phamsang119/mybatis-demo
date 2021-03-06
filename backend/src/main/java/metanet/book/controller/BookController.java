package metanet.book.controller;

import metanet.book.controller.request.DeleteBookRequest;
import metanet.book.controller.response.Paging;
import metanet.book.controller.response.SelectBookResponse;
import metanet.book.dto.Book;
import metanet.book.service.BookService;
import metanet.book.controller.request.CreateBookRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
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
    public SelectBookResponse getAll(@RequestParam(defaultValue = "20") int limit, @RequestParam(defaultValue = "1") int page) {
        long count = bookService.count();
        return new SelectBookResponse(new Paging(page, limit, count), bookService.getAll(page, limit));
    }

    /**
     * Create a new book.
     *
     * @param request the given book request
     * @return an instance of {@link Book}
     */
    @PostMapping()
    public Book create(@RequestBody Book request) {
        return bookService.saveOne(request);
    }


    /**
     * Create multiple books
     *
     * @param request
     */
    @PostMapping(path = "/list")
    public void create(@RequestBody CreateBookRequest request) {
        List<Book> books = request.getListBooks();
        bookService.saveMany(books);
    }

    /**
     * Update a book by id
     *
     * @param request
     * @return
     */
    @PostMapping(path = "/update")
    public Book update(@RequestBody Book request) {
        return bookService.updateOne(request);
    }

    /**
     * Update multiple books
     *
     * @param request
     */
    @PostMapping(path = "/updateList")
    public void update(@RequestBody CreateBookRequest request) {
        List<Book> books = request.getListBooks();
        bookService.updateMany(books);
    }

    /**
     * Delete a book by id.
     *
     * @param id the given book id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteOne(id);
    }

    @DeleteMapping
    public void delete(@RequestBody DeleteBookRequest request) {
        bookService.deleteMany(request.getListId());

    }

    /**
     * Add data
     */
    @GetMapping("/cheat")
    public void cheatData() {
        bookService.cheatData();
    }
}
