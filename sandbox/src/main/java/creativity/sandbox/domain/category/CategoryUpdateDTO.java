package creativity.sandbox.domain.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateDTO {

    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 100)
    private String name;
}
