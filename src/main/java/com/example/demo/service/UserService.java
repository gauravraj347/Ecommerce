package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;
    private Long a=1L;

    public List<User> fetchAllUser(){
        return userRepository.findAll();
    }
    public void addUser(User user ){
//        user.setId(a++);
//        userList.add(user);

        userRepository.save(user);
    }

    public Optional<User> fetchUser(Long id) {
//        for(User user: userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return null;

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id);
    }

    public boolean updatedUser(Long id,User updateUser) {
//        return userList.stream()
//                .filter(user ->user.getId().equals(id))
//                .findFirst()
//                .map(existingUser->{
//                    existingUser.setFirstName(updateUser.getFirstName());
//                    existingUser.setLastName(updateUser.getLastName());
//                    return  true;
//                }).orElse(false);

        return userRepository.findById(id)
                .map(existingUser->{
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    userRepository.save(existingUser);
                    return  true;
                }).orElse(false);
    }
}
