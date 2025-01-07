package com.parwez.controller;

import com.parwez.modal.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/tasks")
    public ResponseEntity<String> home(){
        return new ResponseEntity<>("Welcome To Task Service.", HttpStatus.OK);
    }
}
