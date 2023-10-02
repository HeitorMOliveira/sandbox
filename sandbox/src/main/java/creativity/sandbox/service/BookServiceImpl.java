package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.EntityCreationExistsException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new EntityCreationExistsException("Book already exists: " + book.getTitle());
        }

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
            }
        }

        if (book.getAuthor() != null) {
            Author author = authorRepository.findByName(book.getAuthor().getName()).orElse(authorRepository.save(Author.builder()
                    .name(book.getAuthor().getName())
                    .age(book.getAuthor().getAge())
                    .surname(book.getAuthor().getSurname())
                    .build()));
            bookCreated.setAuthor(author);
        }

        return mapper.bookDTOBuilder(bookRepository.save(bookCreated));
    }


    @Override
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(mapper::bookDTOBuilder);
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

    public BookDTO findByTitle(String title) {
        return mapper.bookDTOBuilder(bookRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException(title)));
    }

    @Override
    public Page<BookDTO> findByAuthorName(String authorName, Pageable pageable) {
        return bookRepository.findByAuthorName(authorName, pageable).map(mapper::bookDTOBuilder);
        //TODO make the search work using the full name too
    }

    @Override
    public Page<BookDTO> findByCategoryName(String categoryName, Pageable pageable) {
        return bookRepository.findByCategory(categoryName, pageable).map(mapper::bookDTOBuilder);
    }

}
