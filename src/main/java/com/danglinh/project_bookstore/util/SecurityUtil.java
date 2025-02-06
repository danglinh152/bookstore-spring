package com.danglinh.project_bookstore.util;


import com.danglinh.project_bookstore.domain.entity.User;
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
import java.util.Optional;


@Service
public class SecurityUtil {

    private final JwtEncoder jwtEncoder;
    public final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;


    @Value("${danglinh.jwt.token-validity-in-seconds}")
    private long jwtValidity;

    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }


    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(jwtValidity);

        JwtClaimsSet claims = JwtClaimsSet.builder().issuedAt(now)
                .expiresAt(expiresAt)
                .claim("test", authentication)
                .subject(authentication.getName())
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
}
