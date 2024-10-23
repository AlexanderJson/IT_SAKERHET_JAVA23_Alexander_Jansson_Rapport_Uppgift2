package org.example.inl;

import org.example.inl.Security.JWT.JwTUtil;
import org.example.inl.transactions.model.Transaction;
import org.example.inl.transactions.model.TransactionDTO;
import org.example.inl.users.model.User;
import org.example.inl.users.model.userDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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


    public void options() {
        System.out.println("1) Register - 2) Login - 3) Delete user - 4) View transactions - 5) Add token - 6) Get token");
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
                            addTransaction();
                            break;
                        case 6:
                            printToken();
                            break;

        }

    }


    private void addTransaction(){
        System.out.println("Product name: ");
        String nameInput = s.nextLine();

        System.out.println("Transaction amount: ");
        String amountInput = s.nextLine();


        TransactionDTO transaction = new TransactionDTO();
        transaction.setName(nameInput);
        transaction.setAmount(amountInput);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TransactionDTO> request = new HttpEntity<>(transaction, headers);

        String url = "http://localhost:8080/transactions/add";
        System.out.println("Amount: " + transaction.getAmount());
        System.out.println("Name: " + transaction.getName());


        String response = restTemplate.postForObject(url,request,String.class);
        System.out.println("RESPONSE" + response);
        options();
    }

    private void viewTransactions() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> request = new HttpEntity<>(headers);


        String url = "http://localhost:8080/transactions/user";
        ResponseEntity<Transaction[]> response = restTemplate.exchange(url, HttpMethod.GET,  request, Transaction[].class);

        Transaction[] transactions = response.getBody();
        if(transactions != null){
            System.out.println("Transaction History: ");
            for(Transaction transaction : transactions){
                System.out.println("Transaction ID: " + transaction.getId());
                System.out.println("User: " + transaction.getUserId());
                System.out.println("Name: " + transaction.getName());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Created At: " + transaction.getCreatedAt());
            }
        }  else {
            System.out.println("No transactions found");
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = "http://localhost:8080/users/remove";
        restTemplate.exchange(url,HttpMethod.DELETE,request,String.class);
        System.out.println("User deleted.");
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
