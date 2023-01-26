package smm.simpleMemo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import smm.simpleMemo.response.ResponseMemo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper;
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    public LoginSuccessHandler(ObjectMapper objectMapper, PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices) {
        this.objectMapper = objectMapper;
        this.persistentTokenBasedRememberMeServices = persistentTokenBasedRememberMeServices;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Remember-me
        if (auth != null) {
            persistentTokenBasedRememberMeServices.loginSuccess(request, response, auth);
        }

        ResponseMemo<String> responseMemo = new ResponseMemo<>(authentication.getName());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), responseMemo);
    }
}
