package metanet.book.repository;

import metanet.book.dto.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookRepository {

    String GET_BOOKS = "SELECT * from books LIMIT #{offset}, #{limit} ";
    String GET_BOOK_BY_ID = "SELECT * from books where id = #{id}";
    String INSERT_BOOK = "INSERT INTO books (id, author, bookName,description,price,published_date, category) VALUES (#{id}, #{author}, #{bookName},#{description},#{price},#{publishedDate},#{category})";
    String INSERT_BOOKS = "<script>" +
            "insert into  books (id, author, bookName,description,price,published_date, category) values " +
            "<foreach collection='list' item='book' index='index' open='(' separator = '),(' close=')' >#{book.id},#{book.author},#{book.bookName},#{book.description},#{book.price},#{book.publishedDate},#{book.category}</foreach>" +
            "</script>";
    String UPDATE_BOOKS = "<script>" +
            "<foreach collection='list' item='book' index='index'  separator=';' >" +
            "UPDATE  books SET " +
            "author=#{book.author},bookName=#{book.bookName}, description=#{book.description}, price=#{book.price}, published_date=#{book.publishedDate}, category=#{book.category}" +
            " WHERE id=#{book.id}</foreach>" +
            "</script>";
    String UPDATE_BOOK = "UPDATE books SET author=#{author},bookName=#{bookName}, description=#{description}, price=#{price}, published_date=#{publishedDate}, category=#{category} where id=#{id}";
    String DELETE_BOOK = "DELETE FROM books where id=#{id}";
    String DELETE_BOOKS = "<script>DELETE FROM books WHERE id IN (<foreach collection='list' item='id' index='index' separator=','>#{id}</foreach>)</script>";
    String COUNT = "SELECT COUNT(*) from books";


    @Select(GET_BOOKS)
    List<Book> getBooks(@Param("offset") int offset, @Param("limit") int limit);

    @Select(GET_BOOK_BY_ID)
    Book getBookById(long id);

    @Insert(INSERT_BOOK)
    //Sets the object id to the id generated in database
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertBook(Book book);

    @Insert({INSERT_BOOKS})
    int insertBooks(@Param("list") List<Book> books);

    @Update(UPDATE_BOOK)
    int updateBook(Book book);

    @Insert({UPDATE_BOOKS})
    int updateBooks(@Param("list") List<Book> books);

    @Delete(DELETE_BOOK)
    void deleteBook(long id);

    @Delete({DELETE_BOOKS})
    void deleteBooks(List list);

    @Select(COUNT)
    long count();
}
