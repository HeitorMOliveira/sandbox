package creativity.sandbox.domain.author;

import creativity.sandbox.domain.book.BookDTO;
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

    private String name;
    private String surname;
    private int age;
    private List<BookDTO> books;
}
