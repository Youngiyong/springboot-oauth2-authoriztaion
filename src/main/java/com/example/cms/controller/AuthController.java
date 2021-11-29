package com.example.cms.controller;

import com.example.cms.dto.BaseResponse;
import com.example.cms.dto.request.LoginRequest;
import com.example.cms.dto.request.SignupRequest;
import com.example.cms.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    UserDetailsServiceImpl userService;

    @PostMapping("/signup")
    public BaseResponse registerUser(@RequestBody SignupRequest payload){
        return userService.save(payload);
    }

}
