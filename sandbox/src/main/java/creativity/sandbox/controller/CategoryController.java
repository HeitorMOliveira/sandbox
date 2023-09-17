package creativity.sandbox.controller;


import creativity.sandbox.domain.category.CategoryCreationDTO;
import creativity.sandbox.domain.category.CategoryDTO;
import creativity.sandbox.domain.category.CategoryUpdateDTO;
import creativity.sandbox.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<CategoryDTO> findAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO save(@RequestBody @Valid CategoryCreationDTO entity) {
        return service.save(entity);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody @Valid CategoryUpdateDTO entity) {
        service.update(id, entity);
    }

    @GetMapping(path = "/findByName")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO findByName(@RequestParam String name) {
        return service.findByName(name);
    }


}
