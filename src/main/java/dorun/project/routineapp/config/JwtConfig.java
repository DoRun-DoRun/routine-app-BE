package dorun.project.routineapp.config;

import dorun.project.routineapp.oauth.token.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public TokenProvider jwtProvider() {
        return new TokenProvider(secret);
    }
}
