package creativity.sandbox.controller;

import creativity.sandbox.domain.book.Book;
import creativity.sandbox.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping(path = "/{id}")
    public Book findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Book> findAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }

    @PostMapping
    public Book save(@RequestBody Book entity) {
        return service.save(entity);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody Book entity) {
        service.update(id, entity);
    }
}
