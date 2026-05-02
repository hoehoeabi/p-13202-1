package com.back.p13202.domain.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    @NotEmpty(message = "이름은 필수입니다이")
    @Size(max = 10)
    private String username;

    @NotEmpty(message = "비밀번호는 필수입니다이")
    private String password;

}
