package metanet.book.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BookEntity implements Serializable {

    private Long id;

    private String bookName;

    private String author;

    private String description;

    private Date publishedDate = new Date();

    private double price;

    private String category;

    public BookEntity(String bookName,
                      String author,
                      String description,
                      Date publishedDate,
                      double price, String category) {
        this.bookName = bookName;
        this.author = author;
        this.description = description;
        this.publishedDate = publishedDate;
        this.price = price;
        this.category = category;
    }

    public BookEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity book = (BookEntity) o;
        return id.equals(book.id) &&
                Objects.equals(bookName, book.bookName);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", description='" + description + '\'' +
                ", publishedDate=" + publishedDate +
                ", price=" + price +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
