package creativity.sandbox.domain.author;

import creativity.sandbox.domain.book.Book;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "author")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private int age;
    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();

}
    //Todo ajustar para usar de alguma forma uuid e id juntos
    //@Id
    //@UuidGenerator
    //private UUID uuid;