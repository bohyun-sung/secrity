package com.security.api.config.jwt;

import com.security.api.config.security.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {



    private AuthorizationExtractor authorizationExtractor;
    private JwtTokenProvider jwtTokenProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authorizationExtractor, JwtTokenProvider jwtTokenProvider) {
        this.authorizationExtractor = authorizationExtractor;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = authorizationExtractor.extract(request, "Bearer");
        if (token.isEmpty()) {
            throw new TokenEmptyException();
        }

        if (!jwtTokenProvider.vaildateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

//        Long id = jwtTokenProvider.getSubjec(token);
//        request.setAttribute("id", id);
        return true;
    }
}