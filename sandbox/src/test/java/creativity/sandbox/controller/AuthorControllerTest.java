package creativity.sandbox.controller;

import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.book.TinyBookDTO;
import creativity.sandbox.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static creativity.sandbox.builders.DataBuilderForTesting.createAuthorDTO;
import static creativity.sandbox.builders.DataBuilderForTesting.createAuthorToCreateDTO;
import static creativity.sandbox.builders.DataBuilderForTesting.createTinyBookDTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @Test
    @DisplayName("returns a author by id")
    void findById() {
        AuthorDTO authorDTO = createAuthorDTO();
        when(authorService.findById(anyInt())).thenReturn(authorDTO);

        AuthorDTO byId = authorController.findById(anyInt());

        assertNotNull(byId);
        assertEquals(byId, authorDTO);
        assertEquals(byId.getId(), authorDTO.getId());
        assertEquals(byId.getName(), authorDTO.getName());
        assertEquals(byId.getSurname(), authorDTO.getSurname());
        assertEquals(byId.getAge(), authorDTO.getAge());
        assertEquals(byId.getBooks(), authorDTO.getBooks());
    }

    @Test
    @DisplayName("returns a page with authors")
    void findAll() {
        Page<AuthorDTO> categoryPage = new PageImpl<AuthorDTO>(List.of(createAuthorDTO()));
        when(authorService.findAll(any())).thenReturn(categoryPage);

        Page<AuthorDTO> resultPage = authorController.findAll(any());

        assertNotNull(resultPage);
        assertEquals(resultPage, categoryPage);
        assertEquals(resultPage.getContent().get(0).getName(), categoryPage.getContent().get(0).getName());
        assertEquals(resultPage.getContent().get(0).getSurname(), categoryPage.getContent().get(0).getSurname());
        assertEquals(resultPage.getContent().get(0).getAge(), categoryPage.getContent().get(0).getAge());
        assertEquals(resultPage.getContent().get(0).getBooks(), categoryPage.getContent().get(0).getBooks());
    }

    @Test
    @DisplayName("deletes a author")
    void delete() {
        assertDoesNotThrow(() -> authorController.delete(1));
    }

    @Test
    @DisplayName("saves a new author")
    void save() {
        AuthorDTO authorDTO = createAuthorDTO();

        when(authorService.save(any((AuthorCreationDTO.class)))).thenReturn(authorDTO);
        AuthorDTO saved = authorController.save(createAuthorToCreateDTO());

        assertNotNull(saved);
        assertEquals(saved.getName(), authorDTO.getName());
        assertEquals(saved.getSurname(), authorDTO.getSurname());
        assertEquals(saved.getAge(), authorDTO.getAge());
        assertEquals(saved.getBooks(), authorDTO.getBooks());
    }

    @Test
    @DisplayName("updates the information of a given author")
    void update() {
        //TODO
    }

    @Test
    @DisplayName("returns a specific author by name")
    void findByName() {
        AuthorDTO authorDTO = createAuthorDTO();
        when(authorService.findByName(anyString())).thenReturn(authorDTO);

        AuthorDTO byName = authorController.findByName(authorDTO.getName());

        assertNotNull(byName);
        assertEquals(byName.getName(), authorDTO.getName());
        assertEquals(byName.getSurname(), authorDTO.getSurname());
        assertEquals(byName.getAge(), authorDTO.getAge());
        assertEquals(byName.getBooks(), authorDTO.getBooks());
    }

    @Test
    @DisplayName("returns a page with the books from the author")
    void findAllBooksById() {
        List<TinyBookDTO> list = List.of(createTinyBookDTO());
        when(authorService.findAllBooksByAuthor(anyInt())).thenReturn(list);

        List<TinyBookDTO> allBooksById = authorController.findAllBooksById(1);

        assertNotNull(allBooksById);
        assertEquals(allBooksById, list);
    }
}