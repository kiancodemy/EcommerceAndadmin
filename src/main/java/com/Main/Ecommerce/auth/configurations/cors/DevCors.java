package com.Main.Ecommerce.auth.configurations.cors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Profile("dev")
// cors configuration for development mode
@Component
public class DevCors {

    public CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));   // allow all origins
        configuration.setAllowedMethods(List.of("*"));   // allow all HTTP methods
        configuration.setAllowedHeaders(List.of("Jwt"));
        configuration.setExposedHeaders(List.of());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // apply to all endpoints
        return source;
    }
}
