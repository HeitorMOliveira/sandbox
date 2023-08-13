package creativity.sandbox.service;

import creativity.sandbox.domain.category.Category;
import creativity.sandbox.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(int id) {
        if (findById(id) != null) {
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public void update(int id, Category newCategory) {
        Category categoryToUpdate = findById(id);

        if (categoryToUpdate != null && Objects.equals(categoryToUpdate.getId(), newCategory.getId())) {
            updateCategoryDetails(categoryToUpdate, newCategory);
            categoryRepository.save(categoryToUpdate);
        } else {
            throw new IllegalArgumentException("Category not found or IDs do not match");
        }
    }

    private void updateCategoryDetails(Category categoryToUpdate, Category newCategory) {
        categoryToUpdate.setName(newCategory.getName());
        categoryToUpdate.setBooks(newCategory.getBooks());
    }
}
