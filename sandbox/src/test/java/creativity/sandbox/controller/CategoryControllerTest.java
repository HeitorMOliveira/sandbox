package creativity.sandbox.controller;

import creativity.sandbox.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }
}