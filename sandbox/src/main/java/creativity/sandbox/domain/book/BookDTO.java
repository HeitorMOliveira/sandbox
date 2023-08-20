package creativity.sandbox.domain.book;

import creativity.sandbox.domain.author.AuthorDTO;
import creativity.sandbox.domain.category.CategoryDTO;
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
public class BookDTO {
    private String title;
    private String identification;
    private AuthorDTO author;
    private Double price;
    private List<String> tags;
    private List<CategoryDTO> categories;
}
