package br.com.fiap.apisphere.user;

import br.com.fiap.apisphere.user.dto.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return  repository.findAll();
    }

    public User create(User user){
        user.setPassword(
               passwordEncoder.encode( user.getPassword() )
        );
        return repository.save(user);
    }

    public UserProfileResponse getProfile(String email) {
        return repository.findByEmail(email)
                .map(UserProfileResponse::new)
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));
    }

    public void updateAvatar(String email, String avatarURL) {
        var user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setAvatar(avatarURL);
        repository.save(user);
    }
}
