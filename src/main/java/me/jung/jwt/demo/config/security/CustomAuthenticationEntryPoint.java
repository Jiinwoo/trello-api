package me.jung.jwt.demo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jung.jwt.demo.domain.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // json 리턴 및 한글깨짐 수정.
        response.setContentType("application/json;charset=utf-8");


        ErrorResponse errorResponse = ErrorResponse.builder().code("-9999")
                .message("해당 리소스에 접근하기 위한 권한이 없습니다.")
                .status(100)
                .build();

        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(errorResponse));

    }
}
