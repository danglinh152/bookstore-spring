package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.request.LoginDTO;
import com.danglinh.project_bookstore.domain.DTO.response.CurrentUserDTO;
import com.danglinh.project_bookstore.domain.DTO.response.ResLoginDTO;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.UserService;
import com.danglinh.project_bookstore.util.SecurityUtil;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    @Value("${danglinh.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }


    @PostMapping("/sign-in")
    @ApiMessage("Sign In")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);

        User currentUser = userService.findUserByUsername(loginDTO.getUsername());

//        create access token
        String accessToken = securityUtil.createAccessToken(currentUser);

//        create refresh token
        String refreshToken = securityUtil.createRefreshToken(currentUser);
        userService.updateRefreshToken(loginDTO.getUsername(), refreshToken);

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        resLoginDTO.setAccess_token(accessToken);
        resLoginDTO.setRefresh_token(refreshToken);
        resLoginDTO.setUserLogin(new ResLoginDTO.UserLogin(currentUser.getUsername(), currentUser.getEmail(), currentUser.getFirstName(), currentUser.getLastName()));

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .path("/")
                .maxAge(refreshTokenValidityInSeconds)
                .httpOnly(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(resLoginDTO);
    }

    @GetMapping("/refresh")
    @ApiMessage("Refresh Token")
    public ResponseEntity<ResLoginDTO> refresh
            (@CookieValue(name = "refresh_token", defaultValue = "abc") String refreshToken)
            throws IdInvalidException {

        if (refreshToken.equals("abc")) {
            throw new IdInvalidException("Do not have any cookie!");
        }

        Jwt jwtRefreshToken = securityUtil.decodeRefreshToken(refreshToken);

        User currentUser = userService.findUserByUsernameAndRefreshToken(jwtRefreshToken.getSubject(), refreshToken);
        if (currentUser == null) {
            throw new IdInvalidException("Invalid refresh token!");
        }

//        create access token
        String accessToken = securityUtil.createAccessToken(currentUser);

//        create refresh token
        String newRefreshToken = securityUtil.createRefreshToken(currentUser);
        userService.updateRefreshToken(currentUser.getUsername(), newRefreshToken);

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        resLoginDTO.setAccess_token(accessToken);
        resLoginDTO.setRefresh_token(newRefreshToken);
        resLoginDTO.setUserLogin(new ResLoginDTO.UserLogin(currentUser.getUsername(), currentUser.getEmail(), currentUser.getFirstName(), currentUser.getLastName()));

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .path("/")
                .maxAge(refreshTokenValidityInSeconds)
                .httpOnly(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(resLoginDTO);
    }

    @PostMapping("/sign-out")
    @ApiMessage("Sign Out")
    public ResponseEntity<String> signOut() throws IdInvalidException {
        String username = (SecurityUtil.getCurrentUser()).orElse("");
        User currentUser = userService.findUserByUsername(username);

        if (currentUser == null) {
            throw new IdInvalidException("Can not Sign out!");
        }

        userService.updateRefreshToken(currentUser.getUsername(), null);


        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", null)
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body("Signed out!");
    }

    @GetMapping("/get-account")
    @ApiMessage("Get Account")
    public ResponseEntity<CurrentUserDTO> getAccount(@CookieValue(name = "refresh_token", defaultValue = "abc") String refreshToken)
            throws IdInvalidException {

        if (refreshToken.equals("abc")) {
            throw new IdInvalidException("Do not have any cookie!");
        }

        Jwt jwtRefreshToken = securityUtil.decodeRefreshToken(refreshToken);

        User currentUser = userService.findUserByUsernameAndRefreshToken(jwtRefreshToken.getSubject(), refreshToken);
        if (currentUser == null) {
            throw new IdInvalidException("Invalid refresh token!");
        }

        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        currentUserDTO.setUsername(currentUser.getUsername());
        currentUserDTO.setEmail(currentUser.getEmail());
        currentUserDTO.setFirstName(currentUser.getFirstName());
        currentUserDTO.setLastName(currentUser.getLastName());

        return ResponseEntity.ok(currentUserDTO);
    }
}
