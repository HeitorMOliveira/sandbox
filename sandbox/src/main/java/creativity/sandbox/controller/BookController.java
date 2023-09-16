package creativity.sandbox.controller;

import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping(path = "/{id}")
    public BookDTO findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<BookDTO> findAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PostMapping
    public BookDTO save(@RequestBody @Valid BookCreationDTO entity) {
        return service.save(entity);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable int id, @RequestBody @Valid BookUpdateDTO entity) {
        service.update(id, entity);
    }
}
