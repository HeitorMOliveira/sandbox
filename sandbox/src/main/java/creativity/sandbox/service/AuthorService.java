package creativity.sandbox.service;

import creativity.sandbox.domain.author.Author;

import java.util.List;

public interface AuthorService {

    Author findById(int id);

    Author save(Author author);

    List<Author> findAll();

    void delete(int id);

    void update(int id, Author newAuthor);


}
