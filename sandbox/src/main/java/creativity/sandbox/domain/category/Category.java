package creativity.sandbox.domain.category;

import creativity.sandbox.domain.book.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.PERSIST})
    private List<Book> books = new ArrayList<>();


    public static Category categoryCreationDTOToEntity(CategoryCreationDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .build();
    }

    public static CategoryDTO toDTO(Category category){
        return CategoryDTO.builder()
                .name(category.getName())
                .id(category.getId())
                .build();
    }

    public static CategoryToBookDTO categoryToBookDTO(Category category){
        return CategoryToBookDTO.builder()
                .name(category.getName())
                .build();
    }

}
