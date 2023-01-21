package smm.simpleMemo.controller;

import smm.simpleMemo.dto.UserDto;
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
        return "join"; // resources.templates 의 join.html 파일을 찾아감
    }

    @PostMapping("join")
    public String postJoinMember(UserDto userDto, Model model) {
        if (userService.join(userDto)) {
            return "redirect:/join";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(Principal principal) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        return "redirect:/login";
    }
 }
