package br.com.fiap.apisphere.auth;

import br.com.fiap.apisphere.user.User;
import br.com.fiap.apisphere.user.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    public static final Algorithm ALGORITHM = Algorithm.HMAC256("assinatura");
    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Token createToken(String email){
        var expirationAt = LocalDateTime.now().plus(1, ChronoUnit.HOURS).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
                .withSubject(email)
                .withExpiresAt(expirationAt)
                .withIssuer("sphere")
                .withClaim("role", "ADMIN")
                .sign(ALGORITHM);
        return new Token(token, email);
    }

    public User getUserFromToken(String token) {
        var email = JWT.require(ALGORITHM)
                .withIssuer("sphere")
                .build()
                .verify(token)
                .getSubject();

        return userRepository.findByEmail(email)
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
