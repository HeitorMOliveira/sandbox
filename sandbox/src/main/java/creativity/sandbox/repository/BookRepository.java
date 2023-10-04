package creativity.sandbox.repository;

import creativity.sandbox.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE LOWER(TRIM(b.author.name)) LIKE LOWER(CONCAT('%', :authorName, '%')) OR LOWER(TRIM(b.author.surname)) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    Page<Book> findByAuthorName(String authorName, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE LOWER(TRIM(c.name)) = LOWER(TRIM(:categoryName))")
    Page<Book> findByCategory(@Param("categoryName") String categoryName, Pageable pageable);

}
