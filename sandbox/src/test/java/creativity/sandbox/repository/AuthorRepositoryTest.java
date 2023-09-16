package creativity.sandbox.repository;

import creativity.sandbox.domain.author.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static creativity.sandbox.builders.DataBuilderForTesting.createAuthor;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Author repository tests")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Should save an Author")
    void saveTest(){
        Author author = createAuthor();
        Author authorSaved = this.authorRepository.save(author);
        assertThat(authorSaved).isNotNull();
        assertThat(authorSaved.getId()).isNotNull();
        assertThat(authorSaved.getName()).isEqualTo(author.getName());
        assertThat(authorSaved.getSurname()).isEqualTo(author.getSurname());
        assertThat(authorSaved.getAge()).isEqualTo(author.getAge());
    }
}