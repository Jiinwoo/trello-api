package me.jung.jwt.demo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jung.jwt.demo.domain.user.Email;
import me.jung.jwt.demo.domain.user.User;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
public class SignupResponseDTO {
    @Valid
    private Email email;
    private String username;

    public SignupResponseDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
