package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.UserSignupDTO;
import com.stgsporting.piehmecup.exceptions.UserAlreadyExistException;
import com.stgsporting.piehmecup.exceptions.UsernameTakenException;
import com.stgsporting.piehmecup.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignupService signupService;

    @PostMapping("")
    public ResponseEntity<Object> signup(@RequestBody UserSignupDTO userSignupDTO) {
        try{
            signupService.signup(userSignupDTO);
            return ResponseEntity.created(URI.create("/signup")).build();
        } catch (Exception e){
            if(e instanceof UserAlreadyExistException || e instanceof UsernameTakenException)
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
