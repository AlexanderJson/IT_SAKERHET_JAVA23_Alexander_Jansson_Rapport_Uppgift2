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
        System.out.println("1) Register - 2) Login");
        int input = s.nextInt();
        s.nextLine();
        switch(input){
            case 1:
                registerUser();
                break;
                case 2:
                    loginUser();
                    break;
        }

    }

    private void loginUser(){


        System.out.println("  email: ");
        String emailInput = s.nextLine();
        System.out.println(" password: ");
        String passwordInput = s.nextLine();

        System.out.println("Email entered: " + emailInput);
        System.out.println("Password entered: " + passwordInput);


        // will return data as body to header
        userDTO consoleUserLogin = new userDTO();
        consoleUserLogin.setEmail(emailInput);
        consoleUserLogin.setPassword(passwordInput);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<userDTO> request = new HttpEntity<>(consoleUserLogin, headers);

        String url = "http://localhost:8080/users/login";
        System.out.println("userDTO email: " + consoleUserLogin.getEmail());
        System.out.println("userDTO password: " + consoleUserLogin.getPassword());


        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println("RESPONSE" + response);
        //TO-DO : reponse entity?

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

        String url = "http://localhost:8080/users/register";
        System.out.println("userDTO email: " + consoleUser.getEmail());
        System.out.println("userDTO password: " + consoleUser.getPassword());


        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println("RESPONSE" + response);
        //TO-DO : reponse entity?

    }
}
