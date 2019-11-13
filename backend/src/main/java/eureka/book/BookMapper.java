package eureka.book;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("Select * from books")
    List<Book> selectAll();

    @Select("Select * from books where id = #{id} ")
    Book selectOne(long id);

    @Insert("INSERT INTO books (id, author, book_name,description,price,published_date) VALUES (#{id}, #{author}, #{bookName},#{description},#{price},#{publishedDate})")
    // Sets the object id to the id generated in database
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insertBook(Book book);


}
