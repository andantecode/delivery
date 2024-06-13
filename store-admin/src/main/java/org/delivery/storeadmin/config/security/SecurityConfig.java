package org.delivery.storeadmin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity // security 활성화
public class SecurityConfig {

    private final List<String> SWAGGER = List.of("/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**");

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // csrf 공격에 대해 disable
                .authorizeRequests(it -> {
                    it.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resource에 대해 모든 요청 허용
                    .mvcMatchers(SWAGGER.toArray(new String[0])).permitAll() // swagger 인증 없이 통과
                    .mvcMatchers("/open-api/**").permitAll()
                    .anyRequest().authenticated(); // 그 외 모든 요청은 인증 사용
                }).formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // hash 방식으로 암호화
        return new BCryptPasswordEncoder();
    }
}
