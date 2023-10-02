package creativity.sandbox.domain;

import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.author.AuthorToBookDTO;
import creativity.sandbox.domain.author.AuthorUpdateDTO;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookToAuthorDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryToBookDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOMapper {
    //Book DTO's
    public BookDTO bookDTOBuilder(Book book) {
        List<CategoryToBookDTO> categoryList = book.getCategories().stream()
                .map(this::categoryToBookDTO)
                .toList();

        BookDTO.BookDTOBuilder bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .price(book.getPrice())
                .identification(book.getIdentification())
                .tags(book.getTags())
                .id(book.getId())
                .categories(categoryList)
                .id(book.getId());

        if (book.getAuthor() != null) {
            bookDTO.author(AuthorToBookDTO.builder()
                    .name(book.getAuthor().getName())
                    .surname(book.getAuthor().getSurname())
                    .build());
        }


        return bookDTO.build();
    }

    public Book bookCreationDTOToEntity(BookCreationDTO bookDTO) {
        return Book.builder()
                .tags(bookDTO.getTags())
                .title(bookDTO.getTitle())
                .title(bookDTO.getTitle())
                .price(bookDTO.getPrice())
                .identification(bookDTO.getIdentification())
                .build();
    }

    public Book bookUpdateDTOToEntity(BookUpdateDTO bookDTO) {
        return Book.builder()
                .tags(bookDTO.getTags())
                .title(bookDTO.getTitle())
                .title(bookDTO.getTitle())
                .price(bookDTO.getPrice())
                .identification(bookDTO.getIdentification())
                .build();
    }
    //Category DTO's

    public CategoryDTO categoryDTOBuilder(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public CategoryToBookDTO categoryToBookDTO(Category category) {
        return CategoryToBookDTO.builder().name(category.getName()).build();
    }

    public Category categoryCreationDTOToEntity(CategoryCreationDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .build();
    }

    public Category categoryUpdateDTOToEntity(CategoryUpdateDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .build();
    }
    //Author DTO's

    public AuthorDTO authorDTOBuilder(Author author) {
        AuthorDTO.AuthorDTOBuilder authorDTO = AuthorDTO.builder()
                .id(author.getId())
                .age(author.getAge())
                .name(author.getName())
                .surname(author.getSurname());

        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            List<BookToAuthorDTO> bookDTOs = new ArrayList<>();
            for (Book b : author.getBooks()) {
                BookToAuthorDTO bookDTO = BookToAuthorDTO.builder()
                        .price(b.getPrice())
                        .title(b.getTitle())
                        .categories(b.getCategories().stream()
                                .map(this::categoryToBookDTO)
                                .toList())
                        .build();
                bookDTOs.add(bookDTO);
            }
            authorDTO.books(bookDTOs);
        }

        return authorDTO.build();
    }


    public Author AuthorCreationDTOToEntity(AuthorCreationDTO creationDTO) {
        return Author.builder()
                .age(creationDTO.getAge())
                .name(creationDTO.getName())
                .surname(creationDTO.getSurname())
                .build();
    }

    public Author AuthorUpdateDTOToEntity(AuthorUpdateDTO creationDTO) {
        return Author.builder()
                .age(creationDTO.getAge())
                .name(creationDTO.getName())
                .surname(creationDTO.getSurname())
                .build();
    }
}
