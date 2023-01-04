package smm.simpleMemo.service;

import smm.simpleMemo.detail.UserDetail;
import smm.simpleMemo.domain.User;
import smm.simpleMemo.repository.UserDslResp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailService implements UserDetailsService {
    private UserDslResp userDslResp;

    public UserDetailService(UserDslResp userDslResp) {
        this.userDslResp = userDslResp;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userDslResp.findByEmailOpt(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 입력한 아이디에 해당하는 사용자를 찾을 수 없습니다."));

        UserDetail userDetail = new UserDetail(user);

        return userDetail;
    }
}
