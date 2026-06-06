package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/users")
    //@RequestMapping(value = "api/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.fetchAllUser(), HttpStatus.OK);
        //return  ResponseEntity.ok(userService.fetchAllUser());

    }
    @GetMapping("api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
//        User user = userService.fetchUser(id);
//        if(user==null)
//            return ResponseEntity.notFound().build();
//        return  ResponseEntity.ok(user);

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody User updatedUser){
        boolean updated = userService.updatedUser(id, updatedUser);
        if(updated)
            return ResponseEntity.ok("Update User Successfully");
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user ){
        userService.addUser(user);
        return  ResponseEntity.ok("USer added Successfully");

    }
}
