package com.scrotifybanking.scrotifybanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Scrotifybanking application.
 */
@SpringBootApplication
@EnableJpaRepositories
public class ScrotifybankingApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ScrotifybankingApplication.class, args);
    }

}
