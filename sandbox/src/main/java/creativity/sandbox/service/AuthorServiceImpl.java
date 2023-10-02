package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.DataIntegrityException;
import creativity.sandbox.controller.exceptions.EntityCreationExistsException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final DTOMapper mapper;

    @Override
    public AuthorDTO findById(int id) {
        return mapper.authorDTOBuilder(authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    @Transactional
    public AuthorDTO save(AuthorCreationDTO author) {
        if (authorRepository.findByName(author.getName()).isPresent()) {
            throw new EntityCreationExistsException("Author already exists: " + author.getName());
        }
        Author authorEntity = mapper.AuthorCreationDTOToEntity(author);
        Author saved = authorRepository.save(authorEntity);

        return mapper.authorDTOBuilder(saved);
    }

    @Override
    public Page<AuthorDTO> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(mapper::authorDTOBuilder);
    }

    @Override
    @Transactional
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
    @Transactional
    public void update(int id, AuthorUpdateDTO newAuthor) {
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        try {
            updateAuthorDetails(authorToUpdate, newAuthor);
            authorRepository.save(authorToUpdate);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public AuthorDTO findByName(String name) {
        return mapper.authorDTOBuilder(authorRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name)));
    }

    private void updateAuthorDetails(Author authorToUpdate, AuthorUpdateDTO newAuthor) {
        if (newAuthor.getName() != null) {
            authorToUpdate.setName(newAuthor.getName());
        }
        if (newAuthor.getAge() != -1) {
            authorToUpdate.setAge(newAuthor.getAge());
        }
        if (newAuthor.getSurname() != null) {
            authorToUpdate.setSurname(newAuthor.getSurname());
        }
    }
}
