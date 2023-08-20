package creativity.sandbox.service;

import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDTO findById(int id);

    BookDTO save(BookCreationDTO book);

    List<BookDTO> findAll();

    void delete(int id);

    void update(int id, BookUpdateDTO newBook);

    Optional<Book> findByTitle(String title);
}
