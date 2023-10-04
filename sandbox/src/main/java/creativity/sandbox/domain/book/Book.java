package creativity.sandbox.domain.book;

import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.author.AuthorToBookDTO;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryToBookDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String identification;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    private Double price;
    private List<String> tags;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    public static BookDTO toDTO(Book book) {
        List<CategoryToBookDTO> categoryList = book.getCategories().stream()
                .map(Category::categoryToBookDTO)
                .toList();

        BookDTO.BookDTOBuilder bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .price(book.getPrice())
                .identification(book.getIdentification())
                .tags(book.getTags())
                .id(book.getId());

        if (book.getAuthor() != null) {
            bookDTO.author(AuthorToBookDTO.builder()
                    .name(book.getAuthor().getName())
                    .surname(book.getAuthor().getSurname())
                    .build());
        }

        if (!categoryList.isEmpty()) {
            bookDTO.categories(categoryList);
        }


        return bookDTO.build();
    }

    public static Book bookCreationDTOToEntity(BookCreationDTO bookDTO) {
        return Book.builder()
                .tags(bookDTO.getTags())
                .title(bookDTO.getTitle())
                .title(bookDTO.getTitle())
                .price(bookDTO.getPrice())
                .identification(bookDTO.getIdentification())
                .build();
    }

    public static TinyBookDTO toTinyDTO(Book book) {
        List<CategoryToBookDTO> categoryList = book.getCategories().stream()
                .map(Category::categoryToBookDTO)
                .toList();

        TinyBookDTO.TinyBookDTOBuilder tbDTO = TinyBookDTO.builder()
                .price(book.getPrice())
                .title(book.getTitle())
                .identification(book.getIdentification())
                .tags(book.getTags());

        if (!categoryList.isEmpty()) {
            tbDTO.categories(categoryList);
        }

        return tbDTO.build();
    }

    //TODO in the future, make the identification mean something with a prefix
    // to diferenciate (ex: manga being MN-XXXX, other books being OB-XXXX)
}
