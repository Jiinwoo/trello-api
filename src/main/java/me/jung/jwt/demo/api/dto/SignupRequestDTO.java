package me.jung.jwt.demo.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jung.jwt.demo.domain.user.Email;
import me.jung.jwt.demo.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class SignupRequestDTO {
    @Valid
    private Email email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    @Builder
    public SignupRequestDTO(Email email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .username(this.username)
                .build();
    }
}
