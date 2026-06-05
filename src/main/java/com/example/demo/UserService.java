package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();
    private Long a=1L;

    public List<User> fetchAllUser(){
        return userList;
    }
    public void addUser(User user ){
        user.setId(a++);
        userList.add(user);

    }

    public Optional<User> fetchUser(Long id) {
//        for(User user: userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return null;

        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
}
