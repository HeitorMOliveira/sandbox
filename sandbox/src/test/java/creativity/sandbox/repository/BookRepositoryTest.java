package creativity.sandbox.repository;

import creativity.sandbox.domain.book.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static creativity.sandbox.builders.DataBuilderForTesting.createBook;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("Book repository tests")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Should save a Book")
    void saveTest(){
        Book book = createBook();
        Book bookSaved = bookRepository.save(book);

        assertThat(bookSaved).isNotNull();
        assertThat(bookSaved.getId()).isNotNull();
        assertThat(bookSaved.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookSaved.getPrice()).isEqualTo(book.getPrice());
        assertThat(bookSaved.getIdentification()).isEqualTo(book.getIdentification());
        assertThat(bookSaved.getTags()).isEqualTo(book.getTags());
        assertThat(bookSaved.getCategories()).isNotNull();
    }

    @Test
    @DisplayName("Should find a book by title")
    void findByTitleTest() {
        Book book = createBook();
        bookRepository.save(book);

        Optional<Book> bookByTitle = bookRepository.findByTitle(book.getTitle());

        assertThat(bookByTitle).isPresent();
        assertThat(bookByTitle.get().getId()).isNotNull();
        assertThat(bookByTitle.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(bookByTitle.get().getPrice()).isEqualTo(book.getPrice());
        assertThat(bookByTitle.get().getIdentification()).isEqualTo(book.getIdentification());
        assertThat(bookByTitle.get().getTags()).isEqualTo(book.getTags());
        assertThat(bookByTitle.get().getCategories()).isNotNull();

    }
    @Test
    @DisplayName("Should delete a book")
    void deleteTest() {
        Book book = createBook();
        Book bookSaved = this.bookRepository.save(book);

        bookRepository.deleteById(bookSaved.getId());

        Optional<Book> deletedBook = bookRepository.findById(bookSaved.getId());
        assertThat(deletedBook).isEmpty();

    }
}