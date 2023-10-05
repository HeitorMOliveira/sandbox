package creativity.sandbox.builders;

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
import creativity.sandbox.domain.book.TinyBookDTO;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryToBookDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataBuilderForTesting {

    //Book
    public static Book createBook() {
        return Book.builder()
                .id(1)
                .title("Mushoku Tensei")
                .price(100.0)
                .identification("#4124")
                .author(createAuthor())
                .tags(Arrays.asList("isekai", "other world"))
                .categories(Collections.singletonList(createCategory()))
                .build();
    }

    public static BookCreationDTO createBookToCreationDTO() {
        return BookCreationDTO.builder()
                .title("Mushoku Tensei")
                .price(100.0)
                .identification("#4124")
                .author(createAuthorToCreateDTO())
                .tags(Arrays.asList("isekai", "other world"))
                .categories(Collections.singletonList(createCategoryCreationDTO()))
                .build();
    }

    public static BookDTO createBookDTO() {
        return BookDTO.builder()
                .id(1)
                .title("Mushoku Tensei")
                .price(100.0)
                .identification("#4124")
                .author(createAuthorToBookDTO())
                .tags(Arrays.asList("isekai", "other world"))
                .categories(Collections.singletonList(createCategoryToBookDTO()))
                .build();
    }

    public static BookUpdateDTO createBookUpdateDTO() {
        return BookUpdateDTO.builder()
                .title("Mushoku Tensei")
                .price(100.0)
                .identification("#4124")
                .author(createAuthorUpdateDTO())
                .tags(Arrays.asList("isekai", "other world"))
                .categories(Collections.singletonList(createCategoryUpdateDTO()))
                .build();
    }

    public static BookToAuthorDTO createBookToAuthorDTO() {
        return BookToAuthorDTO.builder()
                .title("Mushoku Tensei")
                .price(100.0)
                .categories(List.of(createCategoryToBookDTO()))
                .build();
    }

    public static TinyBookDTO createTinyBookDTO() {
        return TinyBookDTO.builder()
                .categories(List.of(createCategoryToBookDTO()))
                .title("Mushoku Tensei")
                .identification("identification")
                .price(100.0)
                .build();
    }


    //Category
    public static Category createCategory() {
        return Category.builder()
                .id(1)
                .name("Fantasy")
                .build();
    }

    public static CategoryDTO createCategoryDTO() {
        return CategoryDTO.builder()
                .name("Fantasy")
                .id(1)
                .build();
    }

    public static CategoryToBookDTO createCategoryToBookDTO() {
        return CategoryToBookDTO.builder()
                .name("Fantasy")
                .build();
    }

    public static CategoryCreationDTO createCategoryCreationDTO() {
        return CategoryCreationDTO.builder()
                .name("Fantasy")
                .build();
    }
    public static CategoryUpdateDTO createCategoryUpdateDTO() {
        return CategoryUpdateDTO.builder()
                .name("Drama")
                .build();
    }

    //Author
    public static Author createAuthor() {
        return Author.builder()
                .id(1)
                .name("Rifujin")
                .surname("na Magonote")
                .age(30)
                .build();
    }

    public static AuthorDTO createAuthorDTO() {
        return AuthorDTO.builder()
                .id(1)
                .name("Rifujin")
                .surname("na Magonote")
                .age(30)
                .books(List.of(createBookToAuthorDTO()))
                .build();
    }

    public static AuthorToBookDTO createAuthorToBookDTO() {
        return AuthorToBookDTO.builder()
                .name("Rifujin")
                .surname("na Magonote")
                .build();
    }

    public static AuthorCreationDTO createAuthorToCreateDTO() {
        return AuthorCreationDTO.builder()
                .name("Rifujin")
                .surname("na Magonote")
                .age(30)
                .build();
    }

    public static AuthorUpdateDTO createAuthorUpdateDTO() {
        return AuthorUpdateDTO.builder()
                .name("Rifujin")
                .surname("na Magonote")
                .age(30)
                .build();
    }



}
