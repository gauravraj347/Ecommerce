package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;
//    private Long a=1L;

    public List<UserResponse> fetchAllUser(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    public void addUser(UserRequest userRequest ){
//        user.setId(a++);
//        userList.add(user);
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
        
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress()!=null){
            Address address= new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }

    public Optional<UserResponse> fetchUser(Long id) {
//        for(User user: userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return null;

//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updatedUser(Long id,UserRequest updateUserRequest) {
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
//                    existingUser.setFirstName(updateUser.getFirstName());
//                    existingUser.setLastName(updateUser.getLastName());
                    updateUserFromRequest(existingUser, updateUserRequest);
                    userRepository.save(existingUser);
                    return  true;
                }).orElse(false);
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse response  = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress()!=null){
            AddressDto addressDto= new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDto);

        }
        return response;

    }

}
