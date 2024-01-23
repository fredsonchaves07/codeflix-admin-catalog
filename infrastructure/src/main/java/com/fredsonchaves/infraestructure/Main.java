package com.fredsonchaves.infraestructure;

import com.fredsonchaves.infraestructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.fredsonchaves"})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class, args);
    }
}
