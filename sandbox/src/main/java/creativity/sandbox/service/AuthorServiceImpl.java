package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.DTOMapper;
import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.author.AuthorUpdateDTO;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.repository.AuthorRepository;
import creativity.sandbox.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final DTOMapper mapper;

    @Override
    public AuthorDTO findById(int id) {
        return mapper.authorDTOBuilder(authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    public AuthorDTO save(AuthorCreationDTO author) {
        Author authorEntity = mapper.AuthorCreationDTOToEntity(author);
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            List<Book> books = author.getBooks().stream()
                    .map(this::getExistingOrNewBook)
                    .collect(Collectors.toList());

            authorEntity.setBooks(books);
        }
        Author saved = authorRepository.save(authorEntity);

        return mapper.authorDTOBuilder(saved);
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream().map(mapper::authorDTOBuilder).collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        if (findById(id) != null) {
            authorRepository.deleteById(id);
        }
    }

    @Override
    public void update(int id, AuthorUpdateDTO newAuthor) {
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if (!newAuthor.getBooks().isEmpty()) {
            List<Book> books = newAuthor.getBooks().stream()
                    .map(this::getExistingOrNewBook)
                    .collect(Collectors.toList());

            authorToUpdate.setBooks(books);
        }

        try {
            updateAuthorDetails(authorToUpdate, newAuthor);
            authorRepository.save(authorToUpdate);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateAuthorDetails(Author authorToUpdate, AuthorUpdateDTO newAuthor) {
        authorToUpdate.setAge(newAuthor.getAge());
        authorToUpdate.setName(newAuthor.getName());
        authorToUpdate.setSurname(newAuthor.getSurname());
        authorToUpdate.setBooks(newAuthor.getBooks().stream().map(mapper::bookUpdateDTOToEntity).collect(Collectors.toList()));
    }

    private <T> Book getExistingOrNewBook(T bookDTO) {
        String title = getDtoTitle(bookDTO);
        Optional<Book> existingBook = bookRepository.findByTitle(title);

        return existingBook.orElseGet(() -> bookRepository.save((Book) bookDTO));
    }

    private <T> String getDtoTitle(T dto) {
        if (dto instanceof BookCreationDTO) {
            return ((BookCreationDTO) dto).getTitle();
        } else if (dto instanceof BookUpdateDTO) {
            return ((BookUpdateDTO) dto).getTitle();
        }
        throw new IllegalArgumentException("Unsupported DTO type");
    }
}
