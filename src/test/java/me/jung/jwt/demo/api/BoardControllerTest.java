package me.jung.jwt.demo.api;

import me.jung.jwt.demo.api.dto.SignupRequestDTO;
import me.jung.jwt.demo.domain.user.Email;
import me.jung.jwt.demo.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(SpringExtension.class)
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void init (@Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder) {
        SignupRequestDTO signupRequestDTO = SignupRequestDTO.builder()
                .email(Email.builder().value("jwjung5038@gmail.com").build())
                .password("1234").username("test_user").build();
        userRepository.save(signupRequestDTO.toEntity(passwordEncoder));
    }

    @BeforeEach //Junit4의 @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))// 필터 추가
                .addFilter(springSecurityFilterChain)
                .alwaysDo(print()) .build();
    }

    @Test
    public void getBoardsWithNoAuth () throws Exception {
        mockMvc.perform(get("/api/boards"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

//    @WithUserDetails("jwjung5038@gmail.com")
//    @Test
//    public void getBoardsWithAuth () throws Exception {
//
//        mockMvc.perform(get("/api/boards"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }




}