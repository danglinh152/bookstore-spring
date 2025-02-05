package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.request.LoginDTO;
import com.danglinh.project_bookstore.domain.DTO.response.AccessTokenDTO;
import com.danglinh.project_bookstore.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        create token
        String accessToken = securityUtil.createToken(authentication);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setAccess_token(accessToken);
        return ResponseEntity.ok(accessTokenDTO);
    }
}
