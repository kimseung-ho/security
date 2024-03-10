package com.cos.security1.service;

import com.cos.security1.dto.JoinDTO;
import com.cos.security1.entity.UserEntity;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){

        boolean isUser = userRepository.existByUsername((joinDTO.getUsername()));
        if(isUser){
            return;
        }// 동일 아이디 체크 후 존재하면 return문으로 돌아가버리게
        
        
        //객체 생성 후 아이디, 비밀번호 입력받아 아이디 생성
        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));//해쉬암호화
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }


}
