package org.example.inl;

import org.example.inl.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Console;
import java.util.Scanner;

@SpringBootApplication

public class InlApplication {


    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(InlApplication.class, args);
        ConsoleApp console = context.getBean(ConsoleApp.class);
        console.options();
    }
}
