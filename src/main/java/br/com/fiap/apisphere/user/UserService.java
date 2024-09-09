package br.com.fiap.apisphere.user;

import br.com.fiap.apisphere.user.dto.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSenderImpl mailSender;

    public List<User> findAll(){
        return  repository.findAll();
    }

    public User create(User user){
        user.setPassword(
               passwordEncoder.encode( user.getPassword() )
        );
        user.setAvatar("https://avatar.iran.liara.run/username?username=" + user.getName());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("sphere@sphere.com");
        email.setTo(user.getEmail());
        email.setSubject("Boas vindas");
        email.setText("""
                    Olá, %s
                    Seja bem vindo ao sphere.
                """.formatted(user.getName()));

        mailSender.send(email);

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

    public void uploadAvatar(String email, MultipartFile file) {
        if(file.isEmpty()){
            throw new RuntimeException("O arquivo não pode estar vazio");
        }

        Path destinationRoot = Path.of("src/main/resources/static/avatars/");
        Path destinationFile = destinationRoot
                .resolve(Paths.get( System.currentTimeMillis() + email + file.getOriginalFilename()))
                .normalize()
                .toAbsolutePath();

        try(InputStream is = file.getInputStream()){
            Files.copy(is, destinationFile);
            System.out.println("Arquivo copiado");

            var baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            var avatarURL = baseUrl + "/users/avatars/" + destinationFile.getFileName();
            updateAvatar(email, avatarURL);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<User> search(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
