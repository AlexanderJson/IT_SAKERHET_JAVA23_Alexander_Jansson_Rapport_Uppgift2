package org.example.inl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class InlApplication {

    public static void main(String[] args) {
        SpringApplication.run(InlApplication.class, args);
    }



    Scanner s = new Scanner(System.in);
    private void registerForm(){
        System.out.println("Please choose your email: ");
        String email = s.nextLine();
        System.out.println("Please choose your password: ");
        String password = s.nextLine();




    }

}
