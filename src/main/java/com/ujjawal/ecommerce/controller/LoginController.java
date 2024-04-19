package com.ujjawal.ecommerce.controller;

import com.ujjawal.ecommerce.global.GlobalData;
import com.ujjawal.ecommerce.model.Role;
import com.ujjawal.ecommerce.model.User;
import com.ujjawal.ecommerce.repository.RoleRepository;
import com.ujjawal.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.List;

@Controller

public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        ///for clear the cart //
        GlobalData.cart.clear();
        return "login" ;
    }


    @GetMapping("/register")
    public String registerGet(){
        return "register";
    }


    @PostMapping("/register")
    ///http servlet req is used to automatically login once user registered///////
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        System.out.println(user.getEmail());
        System.out.println( user.getPassword());
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();

        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        request.login(user.getEmail(),password);
        return  "redirect:/" ;
    }
}
