package com.stgsporting.piehmecup.controllers;

import com.stgsporting.piehmecup.dtos.AuthUserInfo;
import com.stgsporting.piehmecup.dtos.UserLoginDTO;
import com.stgsporting.piehmecup.exceptions.UserNotFoundException;
import com.stgsporting.piehmecup.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO){
        try {
            AuthUserInfo authUserInfo = loginService.verify(userLoginDTO);
            return ResponseEntity.ok().body(authUserInfo);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
