package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.DataIntegrityException;
import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.DTOMapper;
import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.author.AuthorUpdateDTO;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.repository.AuthorRepository;
import creativity.sandbox.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Author saved = authorRepository.save(authorEntity);

        return mapper.authorDTOBuilder(saved);
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream().map(mapper::authorDTOBuilder).collect(Collectors.toList());
    }

    @Override
    public void delete(int id) {
        try {
            Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            for (Book b: author.getBooks()) {
                b.setAuthor(null);
            }
            authorRepository.deleteById(id);
        } catch (
                DataIntegrityException e) {
            throw new DataIntegrityException("It's not possible to delete a Author with books still associated.", e);
        }

    }

    @Override
    public void update(int id, AuthorUpdateDTO newAuthor) {
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
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
    }
}
