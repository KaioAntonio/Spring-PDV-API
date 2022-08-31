package com.pdv.pdv.controller;

import com.pdv.pdv.dto.LoginDTO;
import com.pdv.pdv.dto.ResponseDTO;
import com.pdv.pdv.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CustomUserDetailService userDetailService;

    @PostMapping()
    public ResponseEntity post(@Valid @RequestBody LoginDTO loginData){
        try {
            userDetailService.verifyUserCredentials(loginData);
            return null;
        } catch (Exception error) {
            return new ResponseEntity(new ResponseDTO<>(error.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

}
