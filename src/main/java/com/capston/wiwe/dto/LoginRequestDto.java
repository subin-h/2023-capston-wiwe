package com.capston.wiwe.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.capston.wiwe.entity.user.Authority;
import com.capston.wiwe.entity.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "로그인 요청")
public class LoginRequestDto {


    @NotBlank(message = "{LoginRequestDto.username.notBlank}")
    private String username;

    @NotBlank(message = "{LoginRequestDto.password.notBlank}")
    private String password;

    public User touser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}