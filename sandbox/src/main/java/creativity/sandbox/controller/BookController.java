package creativity.sandbox.controller;

import creativity.sandbox.domain.book.BookCreationDTO;
import creativity.sandbox.domain.book.BookDTO;
import creativity.sandbox.domain.book.BookUpdateDTO;
import creativity.sandbox.service.BookService;
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
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findById(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO> findAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO save(@RequestBody @Valid BookCreationDTO entity) {
        return service.save(entity);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody @Valid BookUpdateDTO entity) {
        service.update(id, entity);
    }

    @GetMapping(path = "/findByTitle")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO findById(@RequestParam String title) {
        return service.findByTitle(title);
    }


}
