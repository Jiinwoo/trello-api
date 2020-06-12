package me.jung.jwt.demo.api.dto;

import lombok.*;
import me.jung.jwt.demo.domain.user.Email;

import javax.validation.Valid;

@Getter
@NoArgsConstructor
public class LoginRequestDTO {
    @Valid
    Email email;
    String password;

    @Builder
    public LoginRequestDTO(Email email, String password) {
        this.email = email;
        this.password = password;
    }
}
