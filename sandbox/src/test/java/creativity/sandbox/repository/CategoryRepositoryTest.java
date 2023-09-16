package creativity.sandbox.repository;

import creativity.sandbox.domain.category.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static creativity.sandbox.builders.DataBuilderForTesting.createCategory;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("Category repository tests")
class CategoryRepositoryTest {

    @Autowired
    public CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should save an Author")
    void saveTest(){
        Category category = createCategory();
        Category categorySaved = categoryRepository.save(category);

        assertThat(categorySaved).isNotNull();
        assertThat(categorySaved.getId()).isNotNull();
        assertThat(categorySaved.getName()).isEqualTo(category.getName());

    }

    @Test
    @DisplayName("Should find a category by name")
    void findByNameTest() {
        Category category = createCategory();
        categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findByName(category.getName());

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("Fantasy");
    }


    @Test
    @DisplayName("Should update a category")
    void updateTest() {
        Category category = createCategory();
        categoryRepository.save(category);

        category.setName("Isekai");

        Category updatedCategory = categoryRepository.save(category);

        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo("Isekai");
    }

    @Test
    @DisplayName("Should delete a category by ID")
    void deleteTest() {
        Category category = createCategory();
        categoryRepository.save(category);

        categoryRepository.deleteById(category.getId());

        Optional<Category> deletedCategory = categoryRepository.findById(category.getId());
        assertThat(deletedCategory).isEmpty();
    }


}