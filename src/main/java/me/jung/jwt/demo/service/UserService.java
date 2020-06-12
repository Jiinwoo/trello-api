package me.jung.jwt.demo.service;

import lombok.RequiredArgsConstructor;
import me.jung.jwt.demo.advice.exception.EmailDuplicationException;
import me.jung.jwt.demo.api.dto.SignupRequestDTO;
import me.jung.jwt.demo.api.dto.SignupResponseDTO;
import me.jung.jwt.demo.domain.user.User;
import me.jung.jwt.demo.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDTO signup (SignupRequestDTO signupRequestDTO) {
        //중복체크
        User user = userRepository.findByEmail(signupRequestDTO.getEmail());
        if(user != null) {
            throw new EmailDuplicationException(user.getEmail().getValue());
        }
        return new SignupResponseDTO(userRepository.save(signupRequestDTO.toEntity(passwordEncoder)));

    }

}
