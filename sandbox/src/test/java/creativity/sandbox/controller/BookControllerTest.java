package creativity.sandbox.controller;

import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.TinyBookDTO;
import creativity.sandbox.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static creativity.sandbox.builders.DataBuilderForTesting.createBookDTO;
import static creativity.sandbox.builders.DataBuilderForTesting.createBookToCreationDTO;
import static creativity.sandbox.builders.DataBuilderForTesting.createTinyBookDTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    @DisplayName("returns a book by id")
    void findById() {
        BookDTO bookDTO = createBookDTO();
        when(bookService.findById(anyInt())).thenReturn(bookDTO);

        BookDTO byId = bookController.findById(anyInt());

        assertNotNull(byId);
        assertEquals(byId, bookDTO);
        assertEquals(byId.getId(), bookDTO.getId());
        assertEquals(byId.getTitle(), bookDTO.getTitle());
        assertEquals(byId.getAuthor(), bookDTO.getAuthor());
        assertEquals(byId.getPrice(), bookDTO.getPrice());
        assertEquals(byId.getCategories(), bookDTO.getCategories());
        assertEquals(byId.getIdentification(), bookDTO.getIdentification());
        assertEquals(byId.getTags(), bookDTO.getTags());
    }

    @Test
    @DisplayName("returns a page of books")
    void findAll() {
        Page<BookDTO> bookPage = new PageImpl<>(List.of(createBookDTO()));
        when(bookService.findAll(any())).thenReturn(bookPage);

        Page<BookDTO> resultPage = bookController.findAll(any());

        assertNotNull(resultPage);
        assertEquals(resultPage, bookPage);
        assertEquals(resultPage.getContent().get(0).getTitle(), bookPage.getContent().get(0).getTitle());
        assertEquals(resultPage.getContent().get(0).getTags(), bookPage.getContent().get(0).getTags());
        assertEquals(resultPage.getContent().get(0).getIdentification(), bookPage.getContent().get(0).getIdentification());
        assertEquals(resultPage.getContent().get(0).getAuthor(), bookPage.getContent().get(0).getAuthor());
        assertEquals(resultPage.getContent().get(0).getCategories(), bookPage.getContent().get(0).getCategories());
        assertEquals(resultPage.getContent().get(0).getPrice(), bookPage.getContent().get(0).getPrice());
        assertEquals(resultPage.getContent().get(0).getId(), bookPage.getContent().get(0).getId());
    }

    @Test
    @DisplayName("deletes a book")
    void delete() {
        assertDoesNotThrow(() -> bookController.delete(1));
    }

    @Test
    @DisplayName("saves a new book")
    void save() {
        BookDTO bookDTO = createBookDTO();
        when(bookService.save(any((BookCreationDTO.class)))).thenReturn(bookDTO);
        BookDTO saved = bookController.save(createBookToCreationDTO());

        assertNotNull(saved);
        assertEquals(saved, bookDTO);
        assertEquals(saved.getId(), bookDTO.getId());
        assertEquals(saved.getTitle(), bookDTO.getTitle());
        assertEquals(saved.getAuthor(), bookDTO.getAuthor());
        assertEquals(saved.getPrice(), bookDTO.getPrice());
        assertEquals(saved.getCategories(), bookDTO.getCategories());
        assertEquals(saved.getIdentification(), bookDTO.getIdentification());
        assertEquals(saved.getTags(), bookDTO.getTags());
    }

    @Test
    @DisplayName("updates the information of a given book")
    void update() {
        //TODO
    }

    @Test
    @DisplayName("retuns a book by title")
    void findByTitle() {
        BookDTO bookDTO = createBookDTO();
        when(bookService.findByTitle(anyString())).thenReturn(bookDTO);

        BookDTO byId = bookController.findByTitle(anyString());

        assertNotNull(byId);
        assertEquals(byId, bookDTO);
        assertEquals(byId.getId(), bookDTO.getId());
        assertEquals(byId.getTitle(), bookDTO.getTitle());
        assertEquals(byId.getAuthor(), bookDTO.getAuthor());
        assertEquals(byId.getPrice(), bookDTO.getPrice());
        assertEquals(byId.getCategories(), bookDTO.getCategories());
        assertEquals(byId.getIdentification(), bookDTO.getIdentification());
        assertEquals(byId.getTags(), bookDTO.getTags());
    }

    @Test
    @DisplayName("returns a page of books by a specific author name or surname")
    void findBooksByAuthorName() {
        Page<BookDTO> bookPage = new PageImpl<>(List.of(createBookDTO()));

        when(bookService.findByAuthorName(anyString(), any())).thenReturn(bookPage);
        Page<BookDTO> booksByAuthorName = bookController.findBooksByAuthorName(anyString(), any());

        assertNotNull(booksByAuthorName);
        assertEquals(booksByAuthorName, bookPage);
        assertEquals(booksByAuthorName.getContent().get(0).getTitle(), bookPage.getContent().get(0).getTitle());
        assertEquals(booksByAuthorName.getContent().get(0).getTags(), bookPage.getContent().get(0).getTags());
        assertEquals(booksByAuthorName.getContent().get(0).getIdentification(), bookPage.getContent().get(0).getIdentification());
        assertEquals(booksByAuthorName.getContent().get(0).getAuthor(), bookPage.getContent().get(0).getAuthor());
        assertEquals(booksByAuthorName.getContent().get(0).getCategories(), bookPage.getContent().get(0).getCategories());
        assertEquals(booksByAuthorName.getContent().get(0).getPrice(), bookPage.getContent().get(0).getPrice());
        assertEquals(booksByAuthorName.getContent().get(0).getId(), bookPage.getContent().get(0).getId());
    }

    @Test
    @DisplayName("returns a page of books by a specifc category name")
    void findBooksByCategoryName() {
        Page<TinyBookDTO> bookPage = new PageImpl<>(List.of(createTinyBookDTO()));

        when(bookService.findByCategoryName(anyString(), any())).thenReturn(bookPage);
        Page<TinyBookDTO> booksByCategoryName = bookController.findBooksByCategoryName(anyString(), any());

        assertNotNull(booksByCategoryName);
        assertEquals(booksByCategoryName, bookPage);
        assertEquals(booksByCategoryName.getContent().get(0).getTitle(), bookPage.getContent().get(0).getTitle());
        assertEquals(booksByCategoryName.getContent().get(0).getTags(), bookPage.getContent().get(0).getTags());
        assertEquals(booksByCategoryName.getContent().get(0).getIdentification(), bookPage.getContent().get(0).getIdentification());
        assertEquals(booksByCategoryName.getContent().get(0).getCategories(), bookPage.getContent().get(0).getCategories());
        assertEquals(booksByCategoryName.getContent().get(0).getPrice(), bookPage.getContent().get(0).getPrice());
    }
}