package creativity.sandbox.service;

import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDTO findById(int id);

    CategoryDTO save(CategoryCreationDTO category);

    List<CategoryDTO> findAll();

    void delete(int id);

    void update(int id, CategoryUpdateDTO newCategory);

    Optional<Category> findByName(String name);
}
