package com.example.news_project.controller;

import com.example.news_project.dto.api_response.ResponseApi;
import com.example.news_project.dto.auth_user.AuthUserDto;
import com.example.news_project.dto.auth_user.ResponseLoginDto;
import com.example.news_project.entity.AuthUser;
import com.example.news_project.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthUserDto authUserDto) throws MessagingException {
        ResponseApi register = authService.register(authUserDto);
        return ResponseEntity.status(register.getCode()).body(register);
    }

    @PutMapping("/verify")
    public ResponseEntity<?> activate(@RequestParam String code, @RequestParam String email) {
        boolean activate = authService.activate(code, email);
        return ResponseEntity.status(activate ? 200 : 404).build();
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String password, @RequestParam String email) {
        ResponseLoginDto login = authService.login(password, email);
        if (login == null) {
            return ResponseEntity.status(404).body("user not found");
        }
        return ResponseEntity.status(200).body(login);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam() String email) {
        ResponseApi responseApi = authService.resetPassword(email);
        return ResponseEntity.status(responseApi.getCode()).body(responseApi);
    }

    @PostMapping("/resetPasswordActivation")
    public ResponseEntity<?> resetPasswordActivation(
            @RequestParam() String code,
            @RequestParam() String email,
            @RequestParam() String password
    ) {
        ResponseApi responseApi = authService.resetPasswordActivation(code, email, password);
        return ResponseEntity.status(responseApi.getCode()).body(responseApi);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal AuthUser authUser){
        ResponseApi responseApi = authService.deleteUser(authUser);
        return ResponseEntity.status(responseApi.getCode()).body(responseApi);
    }

}