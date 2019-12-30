package info.sasinski.security.JwtConfig;

import info.sasinski.security.service.UserPrinciple;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final String _jwtSecret;
    private final int _jwtExpiration;

    public JwtProvider(@Value("${info.sasinski.jwt-secret}") String jwtSecret, @Value("${info.sasinski.jwt-expiration}") int jwtExpiration) {
        _jwtSecret = jwtSecret;
        _jwtExpiration = jwtExpiration;
    }

    public String generateJwtToken(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + _jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, _jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(_jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(_jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
