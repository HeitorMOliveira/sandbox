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
public class TinyBookDTO {
    private String title;
    private String identification;
    private Double price;
    private List<String> tags;
    private List<CategoryToBookDTO> categories;
}
