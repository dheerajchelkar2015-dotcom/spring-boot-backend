package com.security.auth_app_backend.security;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.security.auth_app_backend.entities.Provider;
import com.security.auth_app_backend.entities.RefreshToken;
import com.security.auth_app_backend.entities.User;
import com.security.auth_app_backend.repositories.RefreshTokenRepository;
import com.security.auth_app_backend.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler{


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.auth.frontend.success-redirect}")
    private String frontEndSuccessUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.info("Successfull authetication");
        logger.info(authentication.toString());

        OAuth2User oauth2User =(OAuth2User) authentication.getPrincipal();

        String registrationId = "unknown";
        if(authentication instanceof OAuth2AuthenticationToken token){
            registrationId = token.getAuthorizedClientRegistrationId();
        }

        logger.info("registrationId:"+registrationId);
        logger.info("user:"+oauth2User.getAttributes().toString());

        User user;
        switch (registrationId) {
            case "google" -> {
                String googleId = oauth2User.getAttributes().getOrDefault("sub", "").toString();
                String email = oauth2User.getAttributes().getOrDefault("email", "").toString();
                String name = oauth2User.getAttributes().getOrDefault("name", "").toString();
                String picture = oauth2User.getAttributes().getOrDefault("picture", "").toString();
                User newUser = User.builder()
                .email(email)
                .name(name)
                .image(picture)
                .enable(true)
                .provider(Provider.GOOGLE)
                .providerId(googleId)
                .build();

                user =  userRepository.findByEmail(email).orElseGet(()->userRepository.save(newUser));

            }
        
            default ->{
                throw new RuntimeException("Invalid registration id");
            }
              
        }

        String jti = UUID.randomUUID().toString();
        RefreshToken refreshTokenOb = RefreshToken.builder().jti(jti).user(user).revoked(false).createdAt(Instant.now()).expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds())).build();

        refreshTokenRepository.save(refreshTokenOb);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user, refreshTokenOb.getJti());

        cookieService.attachRefreshCookie(response, refreshToken,(int) jwtService.getRefreshTtlSeconds());

        //response.getWriter().write("Login successfull");
        response.sendRedirect(frontEndSuccessUrl);
    }

}
