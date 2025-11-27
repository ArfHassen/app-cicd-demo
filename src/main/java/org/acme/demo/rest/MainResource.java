package org.acme.demo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainResource {

    @GetMapping("/sayhello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello every body !!!");
    }
}
