package ru.job4j.accidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Egor Bekhterev
 * @date: 24.03.2023
 * @project: job4j_accidents
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("http://localhost:8080/");
    }
}
