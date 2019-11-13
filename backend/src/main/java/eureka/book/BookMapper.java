package eureka.book;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("SELECT * from books")
    List<Book> getBooks();

    @Select("SELECT * from books where id = #{id} ")
    Book getBookById(long id);

    @Insert("INSERT INTO books (id, author, bookName,description,price,published_date, category) VALUES (#{id}, #{author}, #{bookName},#{description},#{price},#{publishedDate},#{category})")
    // Sets the object id to the id generated in database
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertBook(Book book);

    @Update("UPDATE books SET author=#{author},bookName=#{bookName}, description=#{description}, price=#{price}, published_date=#{publishedDate}, category=#{category} where id=#{id}")
    int updateBook(Book book);

    @Update("DELETE FROM books where id=#{id}")
    void deleteBook(long id);
}
