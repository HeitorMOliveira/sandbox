package creativity.sandbox.service;

import creativity.sandbox.controller.exceptions.DataIntegrityException;
import creativity.sandbox.controller.exceptions.EntityCreationExistsException;
import creativity.sandbox.controller.exceptions.ResourceNotFoundException;
import creativity.sandbox.domain.DTOMapper;
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

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final DTOMapper mapper;


    @Override
    public CategoryDTO findById(int id) {
        return mapper.categoryDTOBuilder(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryCreationDTO category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new EntityCreationExistsException("Category already exists: " + category.getName());
        }
        return mapper.categoryDTOBuilder(categoryRepository.save(mapper.categoryCreationDTOToEntity(category)));
    }

    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(mapper::categoryDTOBuilder);
    }

    @Override
    @Transactional
    public void delete(int id) {
        findById(id);
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("It's not possible to delete a category with books still associated.", e);
        }
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
        return mapper.categoryDTOBuilder(categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(name)));
    }

    private void updateCategoryDetails(Category categoryToUpdate, CategoryUpdateDTO newCategory) {
        categoryToUpdate.setName(newCategory.getName());
    }
}
