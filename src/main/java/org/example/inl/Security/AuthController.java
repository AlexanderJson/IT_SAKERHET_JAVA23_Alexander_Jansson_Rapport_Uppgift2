package org.example.inl.Security;

import org.example.inl.Security.JWT.JwTUtil;
import org.example.inl.users.model.userDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
public class AuthController {

    @Autowired
    private JwTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    private void authenticate(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e){
            throw new Exception("Invalid username or password");
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> createJwTToken(@RequestBody userDTO authReq) throws Exception {
        authenticate(authReq.getEmail(), authReq.getPassword());

        String jwtToken = jwtUtil.generateToken(authReq.getEmail());
        System.out.println("Generated token" + jwtToken);
        return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
    }



}

