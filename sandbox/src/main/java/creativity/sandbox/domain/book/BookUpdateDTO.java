package creativity.sandbox.domain.book;

import creativity.sandbox.domain.author.AuthorUpdateDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
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
public class BookUpdateDTO {
    private String title;
    private String identification;
    private AuthorUpdateDTO author;
    private Double price;
    private List<String> tags;
    private List<CategoryUpdateDTO> categories;
}
