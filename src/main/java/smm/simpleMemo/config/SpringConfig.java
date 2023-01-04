package smm.simpleMemo.config;

import smm.simpleMemo.repository.MemoDslResp;
import smm.simpleMemo.repository.TempMemoDslResp;
import smm.simpleMemo.repository.UserDslResp;
import smm.simpleMemo.service.MemoService;
import smm.simpleMemo.service.TempMemoService;
import smm.simpleMemo.service.UserDetailService;
import smm.simpleMemo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {
    private final UserDslResp userDslResp;
    private final MemoDslResp memoDslResp;
    private final TempMemoDslResp tempMemoDslResp;
    private ModelMapper modelMapper;

    @Autowired
    public SpringConfig(UserDslResp userDslResp,
                        MemoDslResp memoDslResp,
                        TempMemoDslResp tempMemoDslResp) {
        this.userDslResp = userDslResp;
        this.memoDslResp = memoDslResp;
        this.tempMemoDslResp = tempMemoDslResp;
    }

    @Bean
    public MemoService boardService() {
        return new MemoService(
                this.memoDslResp);
    }

    @Bean
    public UserDetailService userDetailService() {
        return new UserDetailService(userDslResp);
    }

    @Bean
    public UserService userService() {
        return new UserService(userDslResp);
    }

    @Bean
    public TempMemoService tempMemoService() {
        return new TempMemoService(this.tempMemoDslResp, this.memoDslResp);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper memoModelMapper() {
        return new ModelMapper();
    }
}
