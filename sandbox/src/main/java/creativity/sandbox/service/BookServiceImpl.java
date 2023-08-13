package creativity.sandbox.service;

import creativity.sandbox.domain.book.Book;
import creativity.sandbox.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(int id) {
        if (findById(id) != null) {
            bookRepository.deleteById(id);
        }
    }

    @Override
    public void update(int id, Book newBook) {
        Book bookToUpdate = findById(id);

        if (bookToUpdate != null && Objects.equals(bookToUpdate.getId(), newBook.getId())) {
            updateBookDetails(bookToUpdate, newBook);
            bookRepository.save(bookToUpdate);
        } else {
            throw new IllegalArgumentException("Book not found or IDs do not match");
        }
    }

    private void updateBookDetails(Book bookToUpdate, Book newBook) {
        bookToUpdate.setAuthor(newBook.getAuthor());
        bookToUpdate.setCategories(newBook.getCategories());
        bookToUpdate.setTags(newBook.getTags());
        bookToUpdate.setIdentification(newBook.getIdentification());
        bookToUpdate.setPrice(newBook.getPrice());
        bookToUpdate.setTitle(newBook.getTitle());
    }
}
