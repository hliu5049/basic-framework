package com.framework.security.authorizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.constant.Message;
import com.framework.ommon.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        String errorMessage = "";
        if (e instanceof UsernameNotFoundException) {
            errorMessage = Message.USER_NOT_EXIST;
        } else if (e instanceof BadCredentialsException) {
            errorMessage = Message.AUTHENTICATION_FAILED;
        }
        val objectMapper = new ObjectMapper();
        response.setContentType(new MediaType(MediaType.APPLICATION_JSON, Charset.defaultCharset()).toString());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().println(objectMapper.writeValueAsString(Response.failed(401,errorMessage)));
    }
}
