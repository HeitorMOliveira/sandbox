package creativity.sandbox.domain.book;

import creativity.sandbox.domain.author.AuthorCreationDTO;
import creativity.sandbox.domain.category.CategoryCreationDTO;
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
public class BookCreationDTO {
    private String title;
    private String identification;
    private AuthorCreationDTO author;
    private Double price;
    private List<String> tags;
    private List<CategoryCreationDTO> categories;
}
