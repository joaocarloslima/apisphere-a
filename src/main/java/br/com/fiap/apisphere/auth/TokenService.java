package br.com.fiap.apisphere.auth;

import br.com.fiap.apisphere.user.User;
import br.com.fiap.apisphere.user.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    public Algorithm algorithm;
    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository, @Value("${jwt.secret}") String secret) {
        this.userRepository = userRepository;
        algorithm = Algorithm.HMAC256(secret);
    }

    public Token createToken(String email){
        var expirationAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
                .withSubject(email)
                .withExpiresAt(expirationAt)
                .withIssuer("sphere")
                .withClaim("role", "ADMIN")
                .sign(algorithm);
        return new Token(token, email);
    }

    public User getUserFromToken(String token) {
        var email = JWT.require(algorithm)
                .withIssuer("sphere")
                .build()
                .verify(token)
                .getSubject();

        return userRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
