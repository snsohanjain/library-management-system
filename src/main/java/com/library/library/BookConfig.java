package com.library.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository repository){
        return args -> {
            Book book1 = new Book(1,"The End Of World","SohanJain",true);
            Book book2 = new Book(2,"The End Of Earth","SohanJain",true);

            repository.saveAll(
                    List.of(book1,book2)
            );
        };
    }

}
