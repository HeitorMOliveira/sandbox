package creativity.sandbox.service;

import creativity.sandbox.domain.book.Book;

import java.util.List;

public interface BookService {

    Book findById(int id);

    Book save(Book book);

    List<Book> findAll();

    void delete(int id);

    void update(int id, Book newBook);
}
