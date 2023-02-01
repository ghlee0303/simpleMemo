package smm.simpleMemo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import smm.simpleMemo.dto.MemoDto;
import smm.simpleMemo.dto.UserDto;
import smm.simpleMemo.response.ResponseMemo;
import smm.simpleMemo.service.UserDetailService;
import smm.simpleMemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserController {
    private final UserDetailService userDetailService;
    private final UserService userService;

    public UserController(UserDetailService userDetailService, UserService userService) {
        this.userDetailService = userDetailService;
        this.userService = userService;
    }

    @GetMapping("join")    // http GET 방식의 GET, url
    public String getJoinMember(Model model) {
        return "user/join"; // resources.templates 의 join.html 파일을 찾아감
    }

    @ResponseBody
    @PostMapping("join")
    public ResponseMemo<Boolean> postJoinMember(@RequestBody UserDto userDto) {
        userService.join(userDto);

        return new ResponseMemo<>(true);
    }

    @GetMapping("login")
    public String getLogin(Principal principal) {
        return "user/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {

        return "redirect:user/login";
    }
 }
