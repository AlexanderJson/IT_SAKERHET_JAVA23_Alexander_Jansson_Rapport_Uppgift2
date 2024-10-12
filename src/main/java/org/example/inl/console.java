package org.example.inl;

import org.example.inl.users.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Component
public class console {



    private static final Scanner s = new Scanner(System.in);
    RestTemplate restTemplate;

    public console() {
        restTemplate = new RestTemplate();
    }


    public void Options() {
        System.out.println("1) Register - 2) Delete");
        int input = s.nextInt();
        s.nextLine();
        if (input == 1) {
            registerUser();
        }
    }



    private void registerUser(){
        System.out.println("Please choose your email: ");
        System.out.println("Please choose your email: ");
        String email = s.nextLine();
        System.out.println("Please choose your password: ");
        String password = s.nextLine();

        // will return data as body to header
        User consoleUser = new User();
        consoleUser.setEmail(email);
        consoleUser.setPassword(password);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> request = new HttpEntity<>(consoleUser, headers);

        String url = "http://localhost:8080/register";
        String response = restTemplate.postForObject(url,request,String.class);
        //TO-DO : reponse entity?

    }
}
