package metanet.book.repository;

import metanet.book.dto.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookRepository {

    @Select("SELECT * from books LIMIT #{offset}, #{limit} ")
    List<Book> getBooks(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT * from books where id = #{id} ")
    Book getBookById(long id);

    @Insert("INSERT INTO books (id, author, bookName,description,price,published_date, category) VALUES (#{id}, #{author}, #{bookName},#{description},#{price},#{publishedDate},#{category})")
     //Sets the object id to the id generated in database
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertBook(Book book);

//    @Insert({"<script>",
//            "insert into  books (id, author, bookName,description,price,published_date, category) values ",
//            "<foreach collection='list' item='book' index='index' open='(' separator = '),(' close=')' >#{book.id},#{book.author},#{book.bookName},#{book.description},#{book.price},#{book.publishedDate},#{book.category}</foreach>",
//            "</script>"})
    int insertBooks(@Param("list") List<Book> books);

   @Update("UPDATE books SET author=#{author},bookName=#{bookName}, description=#{description}, price=#{price}, published_date=#{publishedDate}, category=#{category} where id=#{id}")
    int updateBook(Book book);

    @Insert({"<script>",
            "<foreach collection='list' item='book' index='index'  separator=';' >",
            "UPDATE  books SET ",
           "author=#{book.author},bookName=#{book.bookName}, description=#{book.description}, price=#{book.price}, published_date=#{book.publishedDate}, category=#{book.category} WHERE id=#{book.id}</foreach>",
            "</script>"})
   int updateBooks(@Param("list") List<Book> books);

   @Update("DELETE FROM books where id=#{id}")
    void deleteBook(long id);
}
