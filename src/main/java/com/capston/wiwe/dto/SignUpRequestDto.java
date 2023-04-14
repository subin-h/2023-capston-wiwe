package com.capston.wiwe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "회원가입 요청")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;


    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 2, message = "사용자 이름이 너무 짧습니다.")
    private String name;


    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, message = "닉네임이 너무 짧습니다.")
    private String nickname;
}
