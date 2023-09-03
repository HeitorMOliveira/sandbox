package creativity.sandbox.service;

import creativity.sandbox.domain.DTOMapper;
import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.repository.AuthorRepository;
import creativity.sandbox.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static creativity.sandbox.builders.DataBuilderForTesting.createAuthor;
import static creativity.sandbox.builders.DataBuilderForTesting.createBook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private DTOMapper mapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    @DisplayName("Should Author by id")
    void findById() {
        Author author = createAuthor();
        author.setBooks(Collections.singletonList(createBook()));
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        AuthorDTO authorDTO = authorService.findById(1);

        assertNotNull(authorDTO);
        assertEquals(author.getAge(), authorDTO.getAge());
        assertEquals(author.getName(), authorDTO.getName());
        assertEquals(author.getSurname(), authorDTO.getSurname());
        assertEquals(author.getBooks().get(0).getTitle(), authorDTO.getBooks().get(0).getTitle());
        assertEquals(author.getBooks().get(0).getPrice(), authorDTO.getBooks().get(0).getPrice());

    }
}