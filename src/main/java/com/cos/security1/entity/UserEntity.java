package com.cos.security1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter//데이터를 넣고 뺄 수 있게함
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동생성, 유니크
    private int id;

    @Column(unique = true)  // uname 중복제한
    private String username;
    private String password;

    private String role;    //회원 권한
}
