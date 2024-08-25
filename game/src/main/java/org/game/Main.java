package org.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * En esta clase se crea el metodo que genera el contexto de SpringBoot y arranca todos los servicios y endpoints
 */
@SpringBootApplication
@EnableAutoConfiguration
@AutoConfiguration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}