package creativity.sandbox.domain.book;

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
@Builder
public class BookToAuthorDTO {
    private String title;
    private Double price;
    private List<CategoryToBookDTO> categories;
}
