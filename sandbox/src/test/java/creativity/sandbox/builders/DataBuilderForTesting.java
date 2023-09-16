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
                .title("Mushoku Tensei")
                .price(100.0)
                .identification("#4124")
                .tags(Arrays.asList("isekai", "other world"))
                .categories(Collections.singletonList(createCategory()))
                .build();
    }

    //Category
    public static Category createCategory() {
        return Category.builder()
                .name("Fantasy")
                .build();
    }


    //Author
    public static Author createAuthor() {
        return Author.builder()
                .name("Rifujin")
                .surname("na Magonote")
                .age(30)
                .build();
    }


}
