package creativity.sandbox.controller;


import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.category.Category;
import creativity.sandbox.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @PostMapping
    public Category save(@RequestBody Category entity) {
        return service.save(entity);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody Category entity) {
        service.update(id, entity);
    }
}
