package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "All APIs related to login/sign-in & register/sign-up")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login","/signin"})
    @Operation(summary = "API for Login into system",
            description = "This is used to login into system by providing userName & password")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    @GetMapping("")
    public ResponseEntity<JwtAuthResponse> login (@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    @Operation(summary = "API for registering into system",
              description = "This is used to register into system by providing userName & password")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Success")
    public ResponseEntity<String> signup (@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.registerUser(registerDto), HttpStatus.CREATED);
    }

}
