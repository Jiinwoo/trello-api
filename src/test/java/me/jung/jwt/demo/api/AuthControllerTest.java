package me.jung.jwt.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jung.jwt.demo.api.dto.LoginRequestDTO;
import me.jung.jwt.demo.api.dto.SignupRequestDTO;
import me.jung.jwt.demo.domain.user.Email;
import me.jung.jwt.demo.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private Filter springSecurityFilterChain;
    @BeforeEach() //Junit4의 @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))// 필터 추가
                .addFilter(springSecurityFilterChain)
                .alwaysDo(print()) .build();
    }

    @DisplayName("입력인자가 없을 경우 ")
    @Test
    void signupFail () throws Exception {
        // given
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("")
                .username("진우")
                .build();
        // when and then
        mockMvc.perform(post("/api/auth/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDTO))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @DisplayName("중복회원 체크")
    @Test
    void signupDuplicatedEmail () throws Exception {
        // given
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("1234")
                .username("진우")
                .build();
        userRepository.save(signupRequestDTO.toEntity(passwordEncoder));
        SignupRequestDTO signupRequestAnotherDTO = SignupRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("4567")
                .username("정진우")
                .build();
        //when
        mockMvc.perform(post("/api/auth/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestAnotherDTO))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("정상적으로 회원가입")
    @Test
    void signupSuccess () throws Exception {
        // given
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("1234")
                .username("진우")
                .build();
        mockMvc.perform(post("/api/auth/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDTO))
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @DisplayName("로그인")
    @Test
    void loginSuccess() throws Exception {
        // given
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("1234")
                .username("진우")
                .build();
        userRepository.save(signupRequestDTO.toEntity(passwordEncoder));

        LoginRequestDTO loginRequestDTO = LoginRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("1234")
                .build();
        // when and then
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }


}