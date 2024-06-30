package com.mostafa.cashing_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CashingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashingAppApplication.class, args);
    }

}
