package com.Main.Ecommerce.auth.configurations.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtils {


    private final int jwtExpiration = 1000 * 60 * 60;
    private final String secretKey = "Base64EncodefdfdfdffdfdSecretHere";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());



    //// Generate token
    public String generateToken(Map<String, Object> claims) {
        // 1 hour
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration))
                .signWith(key).compact();
    }




    /// Extract body information from jwts toekn
    public Claims extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    /// Check expiration
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /// get expiration date
    private Date extractExpiration(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
    }

}
