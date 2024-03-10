package com.cos.security1.repository;

import com.cos.security1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {    //유저의 id타입을 객체 형식으로

    boolean existByUsername(String username);

    UserEntity findByUsername(String username);
}
