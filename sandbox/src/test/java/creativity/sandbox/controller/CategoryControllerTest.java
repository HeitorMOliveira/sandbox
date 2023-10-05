package creativity.sandbox.controller;

import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static creativity.sandbox.builders.DataBuilderForTesting.createCategory;
import static creativity.sandbox.builders.DataBuilderForTesting.createCategoryCreationDTO;
import static creativity.sandbox.builders.DataBuilderForTesting.createCategoryDTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("returns a category by id")
    void findById() {
        CategoryDTO categoryDTO = createCategoryDTO();
        when(categoryService.findById(anyInt())).thenReturn(categoryDTO);

        CategoryDTO byId = categoryController.findById(anyInt());

        assertNotNull(byId);
        assertEquals(byId, categoryDTO);
        assertEquals(byId.getId(), categoryDTO.getId());
        assertEquals(byId.getName(), categoryDTO.getName());
    }

    @Test
    @DisplayName("returns a page with categories")
    void findAll() {
        Page<CategoryDTO> categoryPage = new PageImpl<>(List.of(createCategoryDTO()));
        when(categoryService.findAll(any())).thenReturn(categoryPage);

        Page<CategoryDTO> resultPage = categoryController.findAll(any());

        assertNotNull(resultPage);
        assertEquals(resultPage, categoryPage);
        assertEquals(resultPage.getContent().get(0).getName(), categoryPage.getContent().get(0).getName());
    }

    @Test
    @DisplayName("deletes a category")
    void delete() {
        assertDoesNotThrow(() -> categoryController.delete(1));
    }

    @Test
    @DisplayName("saves a new category")
    void save() {
        CategoryDTO categoryDTO = createCategoryDTO();

        when(categoryService.save(any((CategoryCreationDTO.class)))).thenReturn(categoryDTO);
        CategoryDTO saved = categoryController.save(createCategoryCreationDTO());

        assertNotNull(saved);
        assertEquals(saved.getName(), createCategory().getName());
    }

    @Test
    @DisplayName("updates a category")
    void update() {
        //TODO

    }

    @Test
    @DisplayName("should find category by name")
    void findByName() {
        CategoryDTO categoryDTO = createCategoryDTO();
        when(categoryService.findByName(anyString())).thenReturn(categoryDTO);

        CategoryDTO byName = categoryController.findByName("Fantasy");

        assertNotNull(byName);
        assertEquals(byName.getName(), createCategory().getName());
    }
}