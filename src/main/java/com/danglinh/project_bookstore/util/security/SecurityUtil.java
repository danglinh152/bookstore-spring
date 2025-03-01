package com.danglinh.project_bookstore.util.security;


import com.danglinh.project_bookstore.domain.DTO.response.ResLoginDTO;
import com.danglinh.project_bookstore.domain.entity.Permission;
import com.danglinh.project_bookstore.domain.entity.Role;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.RoleService;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SecurityUtil {

    private final JwtEncoder jwtEncoder;
    public final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    private final RoleService roleService;


    @Value("${danglinh.jwt.base64-secret}")
    private String jwtKey;

    @Value("${danglinh.jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityInSeconds;

    @Value("${danglinh.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;

    public SecurityUtil(JwtEncoder jwtEncoder, RoleService roleService) {
        this.jwtEncoder = jwtEncoder;
        this.roleService = roleService;
    }


    public String createAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(accessTokenValidityInSeconds);

        String authority = user.getRole().getRoleName();


        JwtClaimsSet claims = JwtClaimsSet.builder().issuedAt(now)
                .expiresAt(expiresAt)
                .claim("authorities", authority)
                .subject(user.getUsername())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String createRefreshToken(User user) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(refreshTokenValidityInSeconds);

        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        userLogin.setUsername(user.getUsername());
        userLogin.setEmail(user.getEmail());
        userLogin.setFirstName(user.getFirstName());
        userLogin.setLastName(user.getLastName());

        JwtClaimsSet claims = JwtClaimsSet.builder().issuedAt(now)
                .expiresAt(expiresAt)
                .claim("infoRefreshToken", userLogin)
                .subject(user.getUsername())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public static Optional<String> getCurrentUser() {
        SecurityContext sc = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(sc.getAuthentication()));
    }

    public static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
//            return jwt.getClaimAsString("sub");// or jwt.getSubject();
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String string) {
            return string;
        } else {
            return null;
        }
    }

    public SecretKey getJwtKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public Jwt decodeRefreshToken(String refreshToken) throws IdInvalidException {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(getJwtKey()).macAlgorithm(JWT_ALGORITHM).build();
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (Exception e) {
            throw new IdInvalidException("Refresh token could not be decoded");
        }
    }
}
