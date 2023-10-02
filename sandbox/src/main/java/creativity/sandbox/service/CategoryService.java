package creativity.sandbox.service;

import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDTO findById(int id);

    CategoryDTO save(CategoryCreationDTO category);

    Page<CategoryDTO> findAll(Pageable pageable);

    void delete(int id);

    void update(int id, CategoryUpdateDTO newCategory);

    CategoryDTO findByName(String name);
}
