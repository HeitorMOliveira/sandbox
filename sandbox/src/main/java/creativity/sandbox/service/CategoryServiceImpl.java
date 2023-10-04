package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.EntityCreationExistsException;
import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import creativity.sandbox.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static creativity.sandbox.domain.category.Category.categoryCreationDTOToEntity;
import static creativity.sandbox.domain.category.Category.toDTO;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryDTO findById(int id) {
        return toDTO(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryCreationDTO category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new EntityCreationExistsException("Category already exists: " + category.getName());
        }
        return toDTO(categoryRepository.save(categoryCreationDTOToEntity(category)));
    }

    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(Category::toDTO);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Category categoryToDelete = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (categoryToDelete.getBooks() != null && !categoryToDelete.getBooks().isEmpty()) {
            categoryToDelete.getBooks().forEach(book -> book.getCategories().remove(categoryToDelete));
        }

        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, CategoryUpdateDTO newCategory) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        try {
            updateCategoryDetails(categoryToUpdate, newCategory);
            categoryRepository.save(categoryToUpdate);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Override
    public CategoryDTO findByName(String name) {
        return toDTO(categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name)));
    }

    private void updateCategoryDetails(Category categoryToUpdate, CategoryUpdateDTO newCategory) {
        categoryToUpdate.setName(newCategory.getName());
    }
}
