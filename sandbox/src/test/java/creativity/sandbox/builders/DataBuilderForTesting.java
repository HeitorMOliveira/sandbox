package creativity.sandbox.builders;

import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.category.Category;

import java.util.Arrays;
import java.util.Collections;

public class DataBuilderForTesting {

    //Book
    public static Book createBook() {
        return Book.builder()
                .title("title")
                .price(100.0)
                .identification("identification")
                .tags(Arrays.asList("tag1", "tag2"))
                .id(1)
                .categories(Collections.singletonList(createCategory()))
                .build();
    }

    //Category
    public static Category createCategory() {
        return Category.builder()
                .name("name")
                .build();
    }


    //Author
    public static Author createAuthor() {
        return Author.builder()
                .surname("surname")
                .name("name")
                .age(30)
                .build();
    }


}
