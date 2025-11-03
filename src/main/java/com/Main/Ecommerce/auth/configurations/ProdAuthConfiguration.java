package com.Main.Ecommerce.auth.configurations;
import com.Main.Ecommerce.auth.configurations.cors.ProdCors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/// security configuration for production mode//////


@Configuration
@RequiredArgsConstructor
@Profile("prod")
public class ProdAuthConfiguration {
    private final ProdCors prodCors;
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(c->c.configurationSource(prodCors.getCorsConfigurationSource())).csrf(AbstractHttpConfigurer::disable).sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(requests -> requests.requestMatchers("/auth").permitAll());
        return (SecurityFilterChain)http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
