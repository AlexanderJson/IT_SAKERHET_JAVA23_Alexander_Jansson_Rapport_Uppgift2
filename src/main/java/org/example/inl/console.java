package org.example.inl;

import org.example.inl.transactions.model.Transaction;
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
    private String jwtToken;
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
                    case 3:
                        deleteUser();
                        break;
                        case 4:
                            viewTransactions();
                            break;
                        case 5:
                            printToken();
                            break;

        }

    }

    private void viewTransactions() {
        System.out.println("write id");
        Long userId = s.nextLong();
        s.nextLine();

        String url = "http://localhost:8080/transactions/user/" + userId;
        Transaction[] transactions = restTemplate.getForObject(url, Transaction[].class);
        if(transactions != null && transactions.length > 0){
            for(Transaction transaction : transactions){
                System.out.println(transaction.getId());
                System.out.println(transaction.getAmount());
                System.out.println(transaction.getDate());
                System.out.println(transaction.getName());
            }
        }
        Options();
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
        viewTransactions();
        //TO-DO : reponse entity?

        if(response.contains("token")){
            jwtToken = response.substring(response.indexOf("token\":\"") + 8, response.length() - 2);
            System.out.println("Token: " + jwtToken);

        }
        Options();

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
        Options();

    }

    public void deleteUser(){
        restTemplate = new RestTemplate();
        System.out.println("Please choose your email: ");
        String emailInputDelete = s.nextLine();
        System.out.println("Email entered: " + emailInputDelete);

        String url = "http://localhost:8080/users/remove/" + emailInputDelete;
        System.out.println(url);
        restTemplate.delete(url);
        Options();

    }

    private void printToken(){
        if(jwtToken != null){
            System.out.println("Current: " + jwtToken);
        } else {
            System.out.println("Current: null");
        }        Options();

    }


}
