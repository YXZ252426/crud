package com.example.crud.controller;

import jakarta.validation.Valid;
import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;
import com.example.crud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }//

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/edit/{id}")//先edit跳转到edit_user，此时user->UserDto->Model进入html
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDto user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserDto userDto) {
        userService.updateUser(userDto);//这里也是用UserDto
        return "redirect:/users";
    }
}