package creativity.sandbox.domain.author;

import creativity.sandbox.domain.book.Book;
import creativity.sandbox.domain.book.BookToAuthorDTO;
import creativity.sandbox.domain.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
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
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private int age;
    @OneToMany(mappedBy = "author",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Book> books = new ArrayList<>();

    public static AuthorDTO toDTO(Author author) {
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
                                .map(Category::categoryToBookDTO)
                                .toList())
                        .build();
                bookDTOs.add(bookDTO);
            }
            authorDTO.books(bookDTOs);
        }

        return authorDTO.build();
    }

    public static Author authorCreationDTOToEntity(AuthorCreationDTO creationDTO) {
        return Author.builder()
                .age(creationDTO.getAge())
                .name(creationDTO.getName())
                .surname(creationDTO.getSurname())
                .build();
    }
}
//Todo ajustar para usar de alguma forma uuid e id juntos
//@Id
//@UuidGenerator
//private UUID uuid;