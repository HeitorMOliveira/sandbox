package creativity.sandbox.service;

import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.domain.book.TinyBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDTO findById(int id);
    BookDTO save(BookCreationDTO book);
    Page<BookDTO> findAll(Pageable pageable);
    void delete(int id);
    void update(int id, BookUpdateDTO newBook);
    BookDTO findByTitle(String title);
    Page<BookDTO> findByAuthorName(String authorName, Pageable pageable);
    Page<TinyBookDTO> findByCategoryName(String categoryName, Pageable pageable);
}
