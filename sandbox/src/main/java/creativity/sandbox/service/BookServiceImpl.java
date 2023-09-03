package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.DTOMapper;
import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import creativity.sandbox.repository.AuthorRepository;
import creativity.sandbox.repository.BookRepository;
import creativity.sandbox.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    private CategoryService categoryService;

    private final DTOMapper mapper;

    @Override
    public BookDTO findById(int id) {
        return mapper.bookDTOBuilder(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    @Transactional
    public BookDTO save(BookCreationDTO book) {
        Book bookCreated = mapper.bookCreationDTOToEntity(book);

        if (book.getCategories() != null) {
            List<Category> categoriesToSave = new ArrayList<>();
            for (CategoryCreationDTO c : book.getCategories()) {
                Optional<Category> byName = categoryRepository.findByName(c.getName());
                if (byName.isEmpty()) {
                    Category saved = categoryRepository.save(Category.builder().name(c.getName()).build());
                    categoriesToSave.add(saved);
                } else {
                    categoriesToSave.add(byName.get());
                }
                bookCreated.setCategories(categoriesToSave);

                if (book.getAuthor() != null) {
                    Author author = authorRepository.findByName(book.getAuthor().getName()).orElse(authorRepository.save(Author.builder()
                            .name(book.getAuthor().getName())
                            .age(book.getAuthor().getAge())
                            .surname(book.getAuthor().getSurname())
                            .build()));
                    bookCreated.setAuthor(author);
                }
            }
        }

        return mapper.bookDTOBuilder(bookRepository.save(bookCreated));
    }


    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(mapper::bookDTOBuilder)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(int id) {
        if (findById(id) != null) {
            bookRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void update(int id, BookUpdateDTO newBook) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (newBook.getCategories() != null) {
            List<Category> categoriesToSave = new ArrayList<>();
            for (CategoryUpdateDTO c : newBook.getCategories()) {
                Optional<Category> byName = categoryRepository.findByName(c.getName());
                if (byName.isEmpty()) {
                    Category saved = categoryRepository.save(Category.builder().name(c.getName()).build());
                    categoriesToSave.add(saved);
                } else {
                    categoriesToSave.add(byName.get());
                }

            }
            bookToUpdate.setCategories(categoriesToSave);
        }
        if (newBook.getAuthor() != null) {
            Optional<Author> authorToUpdate = authorRepository.findByName(newBook.getAuthor().getName());
            if (authorToUpdate.isEmpty()) {
                Author savedAuthor = authorRepository.save(Author.builder()
                        .age(newBook.getAuthor().getAge())
                        .name(newBook.getAuthor().getName())
                        .surname(newBook.getAuthor().getSurname())
                        .build());
                bookToUpdate.setAuthor(savedAuthor);
            } else {
                bookToUpdate.setAuthor(authorToUpdate.get());
            }
        }


        try {
            updateBookDetails(bookToUpdate, newBook);
            bookRepository.save(bookToUpdate);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateBookDetails(Book bookToUpdate, BookUpdateDTO newBook) {
        bookToUpdate.setTags(newBook.getTags());
        bookToUpdate.setIdentification(newBook.getIdentification());
        bookToUpdate.setPrice(newBook.getPrice());
        bookToUpdate.setTitle(newBook.getTitle());
    }

    public Optional<Book> findByTitle(String name) {
        return bookRepository.findByTitle(name);
    }

}
