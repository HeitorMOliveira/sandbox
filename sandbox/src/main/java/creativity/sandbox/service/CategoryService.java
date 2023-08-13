package creativity.sandbox.service;

import creativity.sandbox.domain.category.Category;

import java.util.List;

public interface CategoryService {

    Category findById(int id);

    Category save(Category category);

    List<Category> findAll();

    void delete(int id);

    void update(int id, Category newCategory);
}
