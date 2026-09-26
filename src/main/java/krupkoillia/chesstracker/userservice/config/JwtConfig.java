package krupkoillia.chesstracker.userservice.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.public-key}")
    private RSAPublicKey publicKey;

    @Value("${security.jwt.private-key}")
    private RSAPrivateKey privateKey;

    @Bean
    public JwtEncoder jwtEncoder() {
        return NimbusJwtEncoder
                .withKeyPair(publicKey, privateKey)
                .build();
    }

}
