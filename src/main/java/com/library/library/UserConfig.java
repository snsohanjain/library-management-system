package com.library.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User user1 = new User(1,"Sohanjain",1);
            User user2 = new User(1,"NithinJain",0);


            repository.saveAll(
                    List.of(user1,user2)
            );
        };
    }
}
