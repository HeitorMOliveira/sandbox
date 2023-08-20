package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.DTOMapper;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import creativity.sandbox.repository.BookRepository;
import creativity.sandbox.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    private final DTOMapper mapper;

    @Override
    public BookDTO findById(int id) {
        return mapper.bookDTOBuilder(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    public BookDTO save(BookCreationDTO book) {
        Book bookCreated = mapper.bookCreationDTOToEntity(book);

        if (!book.getCategories().isEmpty()) {
            List<Category> categoriesToSave = book.getCategories()
                    .stream()
                    .map(this::getExistingOrNewCategory)
                    .collect(Collectors.toList());
            bookCreated.setCategories(categoriesToSave);
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
    public void delete(int id) {
        if (findById(id) != null) {
            bookRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void update(int id, BookUpdateDTO newBook) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (!newBook.getCategories().isEmpty()) {
            List<Category> categoriesToSave = newBook.getCategories()
                    .stream()
                    .map(this::getExistingOrNewCategory)
                    .collect(Collectors.toList());
            bookToUpdate.setCategories(categoriesToSave);
        }

        try {
            updateBookDetails(bookToUpdate, newBook);
            bookRepository.save(bookToUpdate);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateBookDetails(Book bookToUpdate, BookUpdateDTO newBook) {
        bookToUpdate.setAuthor(mapper.AuthorUpdateDTOToEntity(newBook.getAuthor()));
        bookToUpdate.setCategories(newBook.getCategories()
                .stream()
                .map(mapper::categoryUpdateDTOToEntity)
                .collect(Collectors.toList()));
        bookToUpdate.setTags(newBook.getTags());
        bookToUpdate.setIdentification(newBook.getIdentification());
        bookToUpdate.setPrice(newBook.getPrice());
        bookToUpdate.setTitle(newBook.getTitle());
    }

    private <T> Category getExistingOrNewCategory(T category) {
        String name = getDtoName(category);
        Optional<Category> categoryToSave = categoryRepository.findByName(name);
        return categoryToSave.orElseGet(() -> categoryRepository.save((Category) category));
    }

    public Optional<Book> findByTitle(String name) {
        return bookRepository.findByTitle(name);
    }

    private <T> String getDtoName(T dto) {
        if (dto instanceof CategoryCreationDTO) {
            return ((CategoryCreationDTO) dto).getName();
        } else if (dto instanceof CategoryUpdateDTO) {
            return ((CategoryUpdateDTO) dto).getName();
        }
        throw new IllegalArgumentException("Unsupported DTO type");
    }

}
