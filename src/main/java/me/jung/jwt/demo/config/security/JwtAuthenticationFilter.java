package me.jung.jwt.demo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import me.jung.jwt.demo.api.dto.LoginRequestDTO;

import java.io.IOException;

import java.util.ArrayList;



public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String ERROR_MESSAGE = "Something went wrong while parsing /login request body";
    private final ObjectMapper objectMapper;
    public JwtAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestDTO credentials = null;
            ServletInputStream inputStream = request.getInputStream();
            credentials = objectMapper.readValue(inputStream,LoginRequestDTO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail().getValue(),
                    credentials.getPassword(),
                    new ArrayList<>()
            );
            Authentication auth = this.getAuthenticationManager().authenticate(authenticationToken);
            return auth;
        }catch (IOException e){
            throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
        }

    }
}
