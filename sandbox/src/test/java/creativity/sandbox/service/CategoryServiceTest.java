package creativity.sandbox.service;

import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static creativity.sandbox.builders.DataBuilderForTesting.createCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("findById returns a category if it exists")
    void findById() {
        Category category = createCategory();

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        CategoryDTO byId = categoryService.findById(1);

        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(category.getName());

    }

    @Test
    void save() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findByName() {
        Category category = createCategory();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        CategoryDTO byId = categoryService.findByName("Fantasy");

        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(category.getName());
    }
}