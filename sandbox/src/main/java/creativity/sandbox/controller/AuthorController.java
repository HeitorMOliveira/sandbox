package creativity.sandbox.controller;

import creativity.sandbox.domain.author.Author;
import creativity.sandbox.domain.book.Book;
import creativity.sandbox.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @GetMapping(path = "/{id}")
    public Author findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Author> findAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @PostMapping
    public Author save(@RequestBody Author entity) {
        return service.save(entity);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody Author entity) {
        service.update(id, entity);
    }

}
