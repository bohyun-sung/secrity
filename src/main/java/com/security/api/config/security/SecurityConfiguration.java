package com.security.api.config.security;

import com.security.api.advice.exception.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf((csrf) -> csrf.disable())
                .httpBasic((httpBasic) -> httpBasic.disable())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests((authorize) ->
                        authorize.antMatchers("/*/signin-user","/*/signup","/*/signin-admin","/*/signin/**","/*/signup/**").permitAll()
                                .antMatchers("/swagger*/**","/social/**").permitAll()
                                .antMatchers("/v1/user*").hasAnyRole("ROLE_USER","MASTER")
                                .antMatchers("/v1/admin*").hasAnyRole("ROLE_ADMIN","MASTER")
                                .antMatchers(HttpMethod.GET, "/helloworld/**", "/excepthion/**").permitAll()
                                .anyRequest().hasRole("USER")
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();

//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
//                .and()
//                .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
//                .antMatchers("/*/signin", "/*/signup").permitAll()// 가입 인증 주소는 누구나 접근 가능
//                .antMatchers("/swagger*/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/helloworld/**", "/exception/**").permitAll()   // helloworld로 시작하는 GET요청 리소소는 누구나 접근가능
//                .anyRequest().hasRole("USER")   // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
//                .and()
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 필터링
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v2/api-docs", "swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**","/swagger-ui/index.html","/swagger-ui/**");
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
//                .and()
//                .authorizeRequests()    // 다음 리퀘스트에 대한 사용권한 체크
//                .antMatchers("/*/signin", "/*/signup").permitAll()// 가입 인증 주소는 누구나 접근 가능
//                .antMatchers("/swagger*/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/helloworld/**", "/exception/**").permitAll()   // helloworld로 시작하는 GET요청 리소소는 누구나 접근가능
//                .anyRequest().hasRole("USER")   // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
//                .and()
//                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 필터링
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v2/api-docs", "swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**","/swagger-ui/index.html","/swagger-ui/**");
//    }
}
