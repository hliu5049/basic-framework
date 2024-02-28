package com.framework.system.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.framework.ommon.Response;
import com.framework.security.JwtService;
import com.framework.system.dto.AuthRequestDTO;
import com.framework.system.vo.JwtResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SystemLoginController {

    @Autowired
    AuthenticationManager authenticate;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/v1/login")
    public Response<JwtResponseVo> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticate.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(!authentication.isAuthenticated()){
            return Response.failed("用户名或密码错误!");
        }
        return Response.success(JwtResponseVo.builder()
                .accessToken(jwtService.generateToken(authRequestDTO.getUsername())).build());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping1")
    public String test1() {
        try {
            Runnable runnable = () -> {
                System.out.println("Hello, www.didispace.com");
            };
            Thread.ofVirtual().name("test-virtual").start(runnable);
            return "ADMIN_ping1";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("123:"+bcryptPasswordEncoder.encode("123456"));
        System.out.println(DateUtil.offset(new Date(), DateField.HOUR, 2));
    }


    @PreAuthorize("hasAuthority('SUPER_USER')")
    @GetMapping("/ping2")
    public String test2() {
        try {
            return "SUPER_USER_ping2";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
