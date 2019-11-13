package com.task.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Book implements Serializable {


    private Long id;


    @JsonProperty("bookName")
    private String bookName;

    private String author;


    private String description;

    @JsonProperty("publishedDate")
    private Date publishedDate = new Date();

    private double price;

    public Book(String bookName,
                String author,
                String description,
                Date publishedDate,
                double price) {
        this.bookName = bookName;
        this.author = author;
        this.description = description;
        this.publishedDate = publishedDate;
        this.price = price;
    }

    public Book() {
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
        Book book = (Book) o;
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
}
