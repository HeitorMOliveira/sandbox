package creativity.sandbox.service;

import creativity.sandbox.domain.author.Author;
import creativity.sandbox.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author findById(int id) {
        return authorRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public void delete(int id) {
        if (findById(id) != null) {
            authorRepository.deleteById(id);
        }
    }

    @Override
    public void update(int id, Author newAuthor) {
        Author authorToUpdate = findById(id);

        if (authorToUpdate != null && Objects.equals(authorToUpdate.getId(), newAuthor.getId())) {
            updateAuthorDetails(authorToUpdate, newAuthor);
            authorRepository.save(authorToUpdate);
        } else {
            throw new IllegalArgumentException("Author not found or IDs do not match");
        }
    }

    private void updateAuthorDetails(Author authorToUpdate, Author newAuthor) {
        authorToUpdate.setAge(newAuthor.getAge());
        authorToUpdate.setName(newAuthor.getName());
        authorToUpdate.setSurname(newAuthor.getSurname());
        authorToUpdate.setBooks(newAuthor.getBooks());
    }
}
