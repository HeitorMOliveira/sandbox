package creativity.sandbox.domain.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import creativity.sandbox.domain.author.AuthorToBookDTO;
import creativity.sandbox.domain.category.CategoryToBookDTO;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BookDTO {
    private int id;
    private String title;
    private String identification;
    private AuthorToBookDTO author;
    private Double price;
    private List<String> tags;
    private List<CategoryToBookDTO> categories;
}
