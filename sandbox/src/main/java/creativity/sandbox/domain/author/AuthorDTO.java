package creativity.sandbox.domain.author;

import creativity.sandbox.domain.book.BookToAuthorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {

    private int id;
    private String name;
    private String surname;
    private int age;
    private List<BookToAuthorDTO> books;
}
