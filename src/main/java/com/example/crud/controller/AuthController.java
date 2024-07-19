package com.example.crud.controller;

//import jakarta.validation.Valid;
import com.example.crud.dto.UserDto;
import com.example.crud.entity.User;
import com.example.crud.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
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
    @GetMapping("/users")//重构users，对邮箱的获取
    public String listUsers(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) String email,
                            Model model) {
        Page<UserDto> userPage;
        if (email != null && !email.isEmpty()) {
            userPage = userService.findUsersByEmailContaining(email, PageRequest.of(page, size));
        } else {
            userPage = userService.findPaginated(PageRequest.of(page, size));
        }
        model.addAttribute("userPage", userPage);
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
    @GetMapping("/add-user")//我现在新增add-user的方式类似于新增了一个原有功能的副本，但是感觉可能太冗余，不够优雅
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "registerInUsers";  // 指向新增用户的表单视图
    }
    @PostMapping("/register/saveInUser")
    public String registrationInUser(@Valid @ModelAttribute("user") UserDto userDto,//这个是不是User的函数，没有对user的操纵所以不需要在UserService添加？
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register/saveInUsers";
        }//

        userService.saveUser(userDto);
        return "redirect:/users";
    }
}*/