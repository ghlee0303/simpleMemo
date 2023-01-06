package smm.simpleMemo.config.security;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import smm.simpleMemo.response.ErrorResult;
import smm.simpleMemo.response.ResponseMemo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper;

    public LoginFailHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ErrorResult errorResult;

        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException || e instanceof UsernameNotFoundException){
            errorResult = new ErrorResult("아이디 또는 비밀번호가 맞지 않습니다.", 601);
        } else {
            errorResult = new ErrorResult("알 수 없는 이유로 로그인이 안되고 있습니다.", 600);
            System.out.println(e.getMessage());
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseMemo<ErrorResult> responseMemo = new ResponseMemo<>(HttpStatus.UNAUTHORIZED.value(), errorResult);

        objectMapper.writeValue(response.getWriter(), responseMemo);
    }
}

/**/

