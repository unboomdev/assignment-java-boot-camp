package com.example.bootcamp.user;

import com.example.bootcamp.user.model.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/address/{userId}")
    public AddressResponse getAddress(@PathVariable int userId) {
        return userService.getAddress(userId);
    }
}
