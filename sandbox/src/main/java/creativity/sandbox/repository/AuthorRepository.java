package creativity.sandbox.repository;

import creativity.sandbox.domain.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Author> findByName(String name);

}
