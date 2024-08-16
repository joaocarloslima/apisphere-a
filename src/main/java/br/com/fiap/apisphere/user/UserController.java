package br.com.fiap.apisphere.user;

import br.com.fiap.apisphere.user.dto.UserRequest;
import br.com.fiap.apisphere.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

}
