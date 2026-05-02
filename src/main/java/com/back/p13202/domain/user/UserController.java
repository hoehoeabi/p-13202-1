package com.back.p13202.domain.user;

import com.back.p13202.global.exception.DuplicateUserName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// com.back.p13202.domain.user.UserController.java

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login_form";
    }

    @GetMapping("/signup")
    public String signup(SignupForm signupForm) {
        return "user/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupForm signupForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/signup_form";
        }

        if (!signupForm.getPassword().equals(signupForm.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "비밀번호 확인이 일치하지 않는다이!");
            return "user/signup_form";
        }

        try {
            userService.signUp(signupForm.getUsername(), signupForm.getPassword(), signupForm.getPasswordConfirm());
        } catch (DuplicateUserName e) {
            bindingResult.rejectValue("username", "duplicate", "이미 존재하는 아이디다이!");
            return "user/signup_form";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", "회원가입 중 오류가 발생했다이!");
            return "user/signup_form";
        }

        return "redirect:/user/login";
    }
}
