package smm.simpleMemo.service;

import smm.simpleMemo.domain.User;
import smm.simpleMemo.dto.UserDto;
import smm.simpleMemo.exception.NotFoundException;
import smm.simpleMemo.repository.UserDslResp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    private UserDslResp userDslResp;

    public UserService(UserDslResp userDslResp) {
        this.userDslResp = userDslResp;
    }

    /**
     * 회원가입
     * 나중에 고치자
     */
    public boolean join(UserDto userDto) {
        if (validateDuplicateMember(userDto.getEmail())) {
            return false;
        }

        User user = new User(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPwd())
        );

        userDslResp.save(user);

        return true;
    }

    public boolean validateDuplicateMember(String name) {
        return userDslResp.findByEmailOpt(name).isPresent();
    }

    public UserDto findById(int id) {
        return modelMapper.map(userDslResp.findByIdOpt(id), UserDto.class);
    }

    public User findUser(int id) {
        return userDslResp.findByIdOpt(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.", 501));
    }
}
