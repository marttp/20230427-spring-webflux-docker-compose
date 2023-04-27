package dev.tpcoder.reactivedockercompose.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public record BookController(BookService bookService) {

    @GetMapping("/{id}")
    public Mono<Book> findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public Flux<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> save(@RequestBody Book payload) {
        Book book = new Book(null, payload.title(), payload.isbn());
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return bookService.deleteById(id).then();
    }
}