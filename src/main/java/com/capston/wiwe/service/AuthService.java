package com.capston.wiwe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.capston.wiwe.config.jwt.TokenProvider;
import com.capston.wiwe.dto.*;
import com.capston.wiwe.entity.user.Authority;
import com.capston.wiwe.entity.user.User;
import com.capston.wiwe.exception.LoginFailureException;
import com.capston.wiwe.exception.MemberNicknameAlreadyExistsException;
import com.capston.wiwe.exception.MemberUsernameAlreadyExistsException;
import com.capston.wiwe.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Transactional
    public User signup(SignUpRequestDto req) throws Exception{
        validateSignUpInfo(req);

        User user = createSignupFormOfUser(req);
        userRepository.save(user);
        return user;
    }


    @Transactional
    public TokenResponseDto signIn(LoginRequestDto req) {
        User user = userRepository.findByUsername(req.getUsername()).orElseThrow(() -> {
            return new LoginFailureException();
        });

        validatePassword(req, user);

        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        String jwt = tokenProvider.generateToken(authentication);

        // 5. 토큰 발급
        return new TokenResponseDto(jwt);
    }



    private void validateSignUpInfo(SignUpRequestDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername()))
            throw new MemberUsernameAlreadyExistsException(registerDto.getUsername());
        if (userRepository.existsByNickname(registerDto.getNickname()))
            throw new MemberNicknameAlreadyExistsException(registerDto.getNickname());
    }

    private void validatePassword(LoginRequestDto loginRequestDto, User user) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new LoginFailureException();
        }
    }
    private User createSignupFormOfUser(SignUpRequestDto req) {
        return User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .nickname(req.getNickname())
                .authority(Authority.ROLE_USER)
                .build();
    }

}