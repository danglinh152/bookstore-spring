package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.request.LoginDTO;
import com.danglinh.project_bookstore.domain.DTO.response.ActivateDTO;
import com.danglinh.project_bookstore.domain.DTO.response.CurrentUserDTO;
import com.danglinh.project_bookstore.domain.DTO.response.ForgotPasswd;
import com.danglinh.project_bookstore.domain.DTO.response.ResLoginDTO;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.EmailService;
import com.danglinh.project_bookstore.service.UserService;
import com.danglinh.project_bookstore.util.security.SecurityUtil;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${danglinh.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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
    @ApiMessage("Get A Account")
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
        currentUserDTO.setAvatar(currentUser.getAvatar());

        return ResponseEntity.ok(currentUserDTO);
    }

    @PostMapping("/register")
    @ApiMessage("Create The Account")
    public ResponseEntity<User> regAccount(@RequestBody User user) {
        if (userService.addUser(user) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/get-activate")
    @ApiMessage("Get Activate Link")
    public ResponseEntity<ActivateDTO> getActivateLink(@Valid @RequestBody LoginDTO loginDTO) throws IdInvalidException {
        User currentUser = userService.findUserByUsername(loginDTO.getUsername());

        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Tạo số từ 0 đến 9
            sb.append(digit);
        }


        try {
            emailService.send("storetenpm@gmail.com", currentUser.getEmail(), "Activate The Account", "Click to activate: http://localhost:8080/activate?acti=" + sb.toString() + "&username=" + loginDTO.getUsername());
            userService.updateActivateCode(currentUser.getUsername(), sb.toString());
            ActivateDTO activateDTO = new ActivateDTO();
            activateDTO.setActivateCode(sb.toString());
            activateDTO.setUserLogin(new ActivateDTO.UserLogin(currentUser.getUsername(), currentUser.getEmail(), currentUser.getFirstName(), currentUser.getLastName()));
            return ResponseEntity.status(HttpStatus.OK).body(activateDTO);
        } catch (IdInvalidException e) {
            throw new IdInvalidException("Can not send email!");
        }
    }

    @GetMapping("/activate")
    @ApiMessage("Activate A Account")
    public ResponseEntity<String> activateAccount(@RequestParam String acti, @RequestParam String username) throws IdInvalidException {
        User currentUser = userService.findUserByUsernameAndActivateCode(username, acti);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Activate Failed!");
        } else {
            currentUser.setActivate(true);
            userService.updateUser(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body("Activate Success!");
        }
    }

    @PostMapping("/forgot-passwd")
    @ApiMessage("Get OTP")
    public ResponseEntity<ForgotPasswd> getOTP(@RequestParam String email) throws IdInvalidException {
        User currentUser = userService.findUserByEmail(email);

        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Tạo số từ 0 đến 9
            sb.append(digit);
        }
        String otp = sb.toString();
        Instant exp = Instant.now().plusSeconds(60);

        currentUser.setOtp(otp);
        currentUser.setOtpExp(exp);
        userService.updateUser(currentUser);

        ForgotPasswd forgotPasswd = new ForgotPasswd(otp, exp);


        try {
            emailService.send("storetenpm@gmail.com", email, "YOUR OTP", otp);
            return ResponseEntity.status(HttpStatus.OK).body(forgotPasswd);
        } catch (IdInvalidException e) {
            throw new IdInvalidException("Can not send email!");
        }
    }

    @PostMapping("/verify-otp")
    @ApiMessage("Verify OTP and update password")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) throws IdInvalidException {
        User currentUser = userService.findUserByEmail(email);

        // Kiểm tra xem OTP có hợp lệ không
        if (currentUser.getOtp() == null || !currentUser.getOtp().equals(otp)) {
            throw new IdInvalidException("Invalid OTP!");
        }

        // Kiểm tra thời gian hết hạn của OTP
        Instant now = Instant.now();
        if (currentUser.getOtpExp().isBefore(now)) {
            throw new IdInvalidException("OTP has expired!");
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10); // Tạo số từ 0 đến 9
            sb.append(digit);
        }
        String newPassword = sb.toString();
        // Cập nhật mật khẩu mới
        currentUser.setPassword(passwordEncoder.encode(newPassword)); // Đảm bảo rằng mật khẩu được mã hóa trước khi lưu
        userService.updateUser(currentUser);

        // Xóa OTP sau khi đã xác minh
        currentUser.setOtp(null);
        currentUser.setOtpExp(null);
        userService.updateUser(currentUser);

        try {
            emailService.send("storetenpm@gmail.com", email, "YOUR new password:", newPassword);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        } catch (IdInvalidException e) {
            throw new IdInvalidException("Can not send email!");
        }

    }

}
