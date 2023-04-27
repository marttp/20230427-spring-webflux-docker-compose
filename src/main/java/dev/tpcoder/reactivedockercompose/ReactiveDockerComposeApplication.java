package dev.tpcoder.reactivedockercompose;

import dev.tpcoder.reactivedockercompose.book.Book;
import dev.tpcoder.reactivedockercompose.book.BookRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootApplication
public class ReactiveDockerComposeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveDockerComposeApplication.class, args);
    }

    @Bean
    public ApplicationRunner loadData(BookRepository bookRepository) {
        List<Book> initialBooks = List.of(
                new Book(null, "The Catcher in the Rye", "0316769487"),
                new Book(null, "To Kill a Mockingbird", "0446310786"),
                new Book(null, "1984", "0451524934"),
                new Book(null, "Animal Farm", "0451526341"),
                new Book(null, "The Great Gatsby", "0743273567"),
                new Book(null, "One Hundred Years of Solitude", "0060883286")
        );
        return args -> bookRepository.deleteAll()
                .thenMany(Flux.fromIterable(initialBooks))
                .flatMap(bookRepository::save)
                .thenMany(bookRepository.findAll())
                .subscribe(System.out::println);
    }
}
