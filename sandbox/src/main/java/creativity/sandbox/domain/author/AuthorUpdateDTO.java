package creativity.sandbox.domain.author;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class AuthorUpdateDTO {

    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @NotEmpty(message = "Surname cannot be empty")
    @Size(min = 3, max = 100)
    private String surname;

    @NotNull
    @NotEmpty(message = "age cannot be empty")
    @Min(10)
    @Max(100)
    private int age;
}
