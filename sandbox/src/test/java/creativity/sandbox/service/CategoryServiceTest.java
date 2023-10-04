package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.EntityCreationExistsException;
import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import creativity.sandbox.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static creativity.sandbox.builders.DataBuilderForTesting.createCategory;
import static creativity.sandbox.builders.DataBuilderForTesting.createCategoryCreationDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    @DisplayName("findById throws a exception if category doesn't exist")
    void findByIdException() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(1));
    }

    @Test
    @DisplayName("saves a new category")
    void save() {
        CategoryCreationDTO creationDTO = createCategoryCreationDTO();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenReturn(createCategory());

        CategoryDTO saved = categoryService.save(creationDTO);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo(creationDTO.getName());
    }

    @Test
    @DisplayName("save throws a exception if the category given already exists")
    void saveThrowsException() {
        CategoryCreationDTO creationDTO = createCategoryCreationDTO();

        Category category = createCategory();
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.ofNullable((category)));

        assertThrows(EntityCreationExistsException.class, () -> categoryService.save(creationDTO));
    }

    @Test
    @DisplayName("returns a page of categories")
    void findAll() {
        Pageable pageable = Pageable.unpaged();
        List<Category> categoryList = List.of(createCategory());
        PageImpl<Category> page = new PageImpl<>(categoryList, pageable, categoryList.size());
        when(categoryRepository.findAll(pageable)).thenReturn(page);

        Page<CategoryDTO> result = categoryService.findAll(pageable);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("deletes a category from the database")
    void delete() {
        Category category = createCategory();

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        categoryService.delete(1);

        verify(categoryRepository).deleteById(1);
    }

    @Test
    @DisplayName("delete throws exception when trying to delete a category that doesn't exists")
    void deleteThrowsResourceNotFoundException() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.delete(1));

        verify(categoryRepository, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("updates a category")
    void update() {
        CategoryUpdateDTO newName = CategoryUpdateDTO.builder().name("new name").build();
        Category category = createCategory();
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        categoryService.update(1, newName);

        assertThat(category.getName()).isEqualTo(newName.getName());
    }

    @Test
    @DisplayName("update throws a exception when a category doesn't exists")
    void updateThrowsException() {
        CategoryUpdateDTO newName = CategoryUpdateDTO.builder().name("new name").build();
        Category category = createCategory();
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.update(1, newName));
    }

    @Test
    @DisplayName("returns a category by name")
    void findByName() {
        Category category = createCategory();

        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        CategoryDTO byId = categoryService.findByName("Fantasy");

        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(category.getName());
    }
}