package br.com.fiap.apisphere.user;

import br.com.fiap.apisphere.user.dto.UserProfileResponse;
import br.com.fiap.apisphere.user.dto.UserRequest;
import br.com.fiap.apisphere.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping
    public List<User> findAll(){
        return  service.findAll();
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest, UriComponentsBuilder uriBuilder){
        var user = service.create(userRequest.toModel());
        var uri = uriBuilder
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(UserResponse.from(user));
    }

    @GetMapping("profile")
    public UserProfileResponse getProfile(){
        // pegar o usuário logado
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return service.getProfile(email);
    }

    @PostMapping("avatar")
    public void uploadAvatar(@RequestBody MultipartFile file){
        if(file.isEmpty()){
            throw new RuntimeException("O arquivo não pode estar vazio");
        }

        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Path destinationRoot = Path.of("src/main/resources/static/avatars/");
        Path destinationFile = destinationRoot
                .resolve(Paths.get( System.currentTimeMillis() + email + file.getOriginalFilename()))
                .normalize()
                .toAbsolutePath();

        try(InputStream is = file.getInputStream()){
            Files.copy(is, destinationFile);
            System.out.println("Arquivo copiado");

            var avatarURL = "http://localhost:8082/avatars/" + destinationFile.getFileName();
            service.updateAvatar(email, avatarURL);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }




    }

}
