package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "user_table")
//@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role = UserRole.CUSTOMER;

//    public User(){
//
//    }
//
//    public User(Long id, String firstName, String lastName) {
//        this.id = id;
//        FirstName = firstName;
//        LastName = lastName;
//    }
    //use @NoArgsConstructor
    //@AllArgsConstructor
}