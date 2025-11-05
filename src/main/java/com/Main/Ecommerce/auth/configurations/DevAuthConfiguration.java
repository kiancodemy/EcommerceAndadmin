package com.Main.Ecommerce.auth.configurations;
import com.Main.Ecommerce.auth.configurations.cors.DevCors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

///  Security configuration for development mode//////
@Configuration
@Profile("dev")
@EnableWebSecurity
@RequiredArgsConstructor
public class DevAuthConfiguration {
    private final DevCors devCors;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomerUserDetailService customerUserDetailService;
    @Bean

    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(c->c.configurationSource(devCors.getCorsConfigurationSource())).csrf(AbstractHttpConfigurer::disable).sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
        return (SecurityFilterChain)http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(){
        return new ProviderManager(List.of(customAuthenticationProvider));
    }




}
