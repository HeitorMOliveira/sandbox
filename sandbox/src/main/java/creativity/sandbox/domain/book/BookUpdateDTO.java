package creativity.sandbox.domain.book;

import creativity.sandbox.domain.author.AuthorUpdateDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 3, max = 80)
    private String title;

    @NotNull
    @NotEmpty(message = "Identification cannot be empty")
    @Size(min = 5, max = 80)
    private String identification;

    @NotNull
    @NotEmpty(message = "Author cannot be empty")
    private AuthorUpdateDTO author;

    @NotNull
    @NotEmpty(message = "Price cannot be empty")
    @Min(1)
    private Double price;

    @NotNull
    private List<String> tags;

    @NotNull
    @NotEmpty(message = "Category cannot be empty")
    private List<CategoryUpdateDTO> categories;
}
