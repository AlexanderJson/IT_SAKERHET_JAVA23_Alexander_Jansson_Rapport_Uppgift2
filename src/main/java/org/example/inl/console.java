package org.example.inl;

import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
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
        String emailInput = s.nextLine();
        System.out.println("Please choose your password: ");
        String passwordInput = s.nextLine();

        System.out.println("Email entered: " + emailInput);
        System.out.println("Password entered: " + passwordInput);


        // will return data as body to header
        userDTO consoleUser = new userDTO();
        consoleUser.setEmail(emailInput);
        consoleUser.setPassword(passwordInput);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<userDTO> request = new HttpEntity<>(consoleUser, headers);

        String url = "http://localhost:8080/register";
        System.out.println("userDTO email: " + consoleUser.getEmail());
        System.out.println("userDTO password: " + consoleUser.getPassword());


        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println("RESPONSE" + response);
        //TO-DO : reponse entity?

    }
}
