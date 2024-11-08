package org.example.inl;

import org.example.inl.Security.JWT.JwTUtil;
import org.example.inl.Messages.model.Messages;
import org.example.inl.Messages.model.MessagesDTO;
import org.example.inl.users.model.userDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Component
public class ConsoleApp {


    private static final Scanner s = new Scanner(System.in);
    RestTemplate restTemplate;
    private String jwtToken;

    @Autowired
    private JwTUtil jwTUtil;

    public ConsoleApp() {
        restTemplate = new RestTemplate();}


    public void options()  {

                System.out.println("--------------- CONSOLE VERSION -------------------");
                System.out.println("1) Register - 2) Login - 3) Delete user - 4) View messages - 5) Write message  -  6) Logout");
                int input = s.nextInt();
                s.nextLine();
                switch(input) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loginUser();
                        break;
                    case 3:
                        deleteUser();
                        break;
                    case 4:
                        viewMessages();
                        break;
                    case 5:
                        addMessage();
                        break;

                    case 6:
                        logout();
                        break;
                    default:

                               /* case 7:
                                    printToken();                   // Använd denna metod för att debug så JWT token faktiskt skapas och är detsamma under session
                                    break; */

        }

    }



    private void addMessage(){
        System.out.println("Message: ");
        String messageInput = s.nextLine();

        if (messageInput != null && !messageInput.isEmpty()) {
            try {
                MessagesDTO message = new MessagesDTO();
                message.setMessage(messageInput);


                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + jwtToken);
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<MessagesDTO> request = new HttpEntity<>(message, headers);

                String url = "http://localhost:8080/messages/add";
                System.out.println("Message: " + message.getMessage());


                String response = restTemplate.postForObject(url, request, String.class);
                System.out.println("RESPONSE" + response);
            }


            catch (HttpClientErrorException.Unauthorized e) {
                System.out.println("Unauthorized, invalid user.");

            }
        } else {
            System.out.println("Invalid input");
        }

        options();
    }

    private void viewMessages() {

        try{

            if(jwtToken == null){
                System.out.println("JWT token either missing or expired. Please login again.");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            HttpEntity<String> request = new HttpEntity<>(headers);


            String url = "http://localhost:8080/messages/user";
            ResponseEntity<Messages[]> response = restTemplate.exchange(url, HttpMethod.GET,  request, Messages[].class);

            Messages[] messages = response.getBody();
            if(messages != null && messages.length > 0){
                System.out.println("Messages History: ");
                for(Messages message : messages){
                    System.out.println("Message ID: " + message.getId());
                    System.out.println("User: " + message.getUserId());
                    System.out.println("Message: " + message.getMessage());
                    System.out.println("Created At: " + message.getCreatedAt());
                }
            }  else {
                System.out.println("No messages found");
            }
        }catch (HttpClientErrorException.Unauthorized e) {
            System.out.println("Unauthorized, invalid user.");
        }

        options();
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

        String url = "http://localhost:8080/authenticate";
        System.out.println("userDTO email: " + consoleUserLogin.getEmail());
        System.out.println("userDTO password: " + consoleUserLogin.getPassword());


        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println("RESPONSE" + response);
        //TO-DO : reponse entity?

        assert response != null;
        if(response.contains("token")){
            jwtToken = response.substring(response.indexOf("token\":\"") + 8, response.length() - 2);
            System.out.println("Token: " + jwtToken);
        }
        options();

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
        options();

    }




    public void deleteUser(){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);
            HttpEntity<String> request = new HttpEntity<>(headers);
            String url = "http://localhost:8080/users/remove";
            restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            System.out.println("User deleted.");
        }
        catch (HttpClientErrorException.Unauthorized e) {
            System.out.println("Unauthorized, invalid user.");
        }
        options();

    }

    public void logout(){
        jwtToken = null;
        System.out.println("-----------------------------User logged out-------------------------------------");
        options();
    }

    // för debugging
    private void printToken(){
        if(jwtToken != null){
            System.out.println("Current: " + jwtToken);
        } else {
            System.out.println("Current: null : no token");
        }
        options();

    }


}
