package creativity.sandbox.repository;

import creativity.sandbox.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);

    Page<Book> findByAuthorName(String authorName, Pageable pageable);
}
