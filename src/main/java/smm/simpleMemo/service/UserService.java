package smm.simpleMemo.service;

import org.apache.commons.lang3.StringUtils;
import smm.simpleMemo.domain.User;
import smm.simpleMemo.dto.UserDto;
import smm.simpleMemo.exception.NotFoundException;
import smm.simpleMemo.exception.ValidateException;
import smm.simpleMemo.repository.UserDslResp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Transactional
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    private UserDslResp userDslResp;
    private String emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

    public UserService(UserDslResp userDslResp) {
        this.userDslResp = userDslResp;
    }

    /**
     * 회원가입
     * 나중에 고치자
     */
    public boolean join(UserDto userDto) {
        if (StringUtils.isEmpty(userDto.getEmail())) {
            throw new ValidateException("아이디를 입력해주세요.", 811);
        }

        if (StringUtils.isEmpty(userDto.getPwd())) {
            throw new ValidateException("비밀번호를 입력해주세요.", 812);
        }

        checkEmail(userDto.getEmail());
        checkPassword(userDto.getPwd());

        User user = new User(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPwd())
        );

        userDslResp.save(user);

        return true;
    }

    private void checkEmail(String email) {
        if ( !Pattern.matches(emailPattern, email) ) {
            throw new ValidateException("잘못된 이메일 입니다.", 813);
        }

        if (duplicateValidate(email)) {
            throw new ValidateException("중복된 이메일 입니다.", 814);
        }
    }

    private void checkPassword(String pwd){
        int pwdErrCode = 815;
        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*).{8,16}$");
        Matcher passMatcher1 = passPattern1.matcher(pwd);

        if(!passMatcher1.find()){
            throw new ValidateException("비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상, 16자 이하여야 합니다.", pwdErrCode);
        }

        // 반복된 문자 확인
        Pattern passPattern2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher passMatcher2 = passPattern2.matcher(pwd);

        if(passMatcher2.find()){
            throw new ValidateException("비밀번호에 동일한 문자를 과도하게 연속해서 사용할 수 없습니다.", pwdErrCode);
        }


        // 특수문자 확인
        Pattern passPattern3 = Pattern.compile("\\W");
        Pattern passPattern4 = Pattern.compile("[!@#$%^_*+=-]");

        for(int i = 0; i < pwd.length(); i++){
            String s = String.valueOf(pwd.charAt(i));
            Matcher passMatcher3 = passPattern3.matcher(s);

            if(passMatcher3.find()){
                Matcher passMatcher4 = passPattern4.matcher(s);
                if(!passMatcher4.find()){
                    throw new ValidateException("비밀번호에 특수문자는 !@#$^_*+=-만 사용 가능합니다.", pwdErrCode);
                }
            }
        }

        //연속된 문자 확인
        int ascSeqCharCnt = 0; // 오름차순 연속 문자 카운트
        int descSeqCharCnt = 0; // 내림차순 연속 문자 카운트

        char char_0;
        char char_1;
        char char_2;

        int diff_0_1;
        int diff_1_2;

        for(int i = 0; i < pwd.length()-2; i++){
            char_0 = pwd.charAt(i);
            char_1 = pwd.charAt(i+1);
            char_2 = pwd.charAt(i+2);

            diff_0_1 = char_0 - char_1;
            diff_1_2 = char_1 - char_2;

            if(diff_0_1 == 1 && diff_1_2 == 1){
                ascSeqCharCnt += 1;
            }

            if(diff_0_1 == -1 && diff_1_2 == -1){
                descSeqCharCnt -= 1;
            }
        }

        if(ascSeqCharCnt > 1 || descSeqCharCnt > 1){
            throw new ValidateException("비밀번호에 연속된 문자열을 사용할 수 없습니다.", pwdErrCode);
        }
    }

    public boolean duplicateValidate(String email) {
        return userDslResp.findByEmailOpt(email).isPresent();
    }

    public User findUser(int id) {
        return userDslResp.findByIdOpt(id)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다.", 501));
    }
}
