package me.jung.jwt.demo.api;

import lombok.RequiredArgsConstructor;
import me.jung.jwt.demo.api.dto.SignupRequestDTO;
import me.jung.jwt.demo.api.dto.SignupResponseDTO;
import me.jung.jwt.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    SignupResponseDTO signup (@RequestBody @Valid SignupRequestDTO signupRequestDTO) {
        return userService.signup(signupRequestDTO);
    }
}
