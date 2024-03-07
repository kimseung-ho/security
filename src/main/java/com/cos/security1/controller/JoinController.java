package com.cos.security1.controller;

import com.cos.security1.dto.JoinDTO;
import com.cos.security1.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    private JoinService joinService;    //필드주입방식인데 생성자 주입방식을 사용하길 바람


    @GetMapping("/join")
    public String JoinP(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        System.out.println(joinDTO.getUsername());

        joinService.joinProcess(joinDTO);

        return "redirecting:/login";
    }
}
