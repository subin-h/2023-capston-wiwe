package com.capston.wiwe.entity.user;

import com.capston.wiwe.entity.Auditor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "user")
public class User extends Auditor {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public User(String username, String password, String nickname, String name, Authority authority) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.authority = authority;
    }


}