package br.com.fiap.apisphere.post;

import br.com.fiap.apisphere.post.dto.PostRequest;
import br.com.fiap.apisphere.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public Page<Post> findAll(@PageableDefault(size = 3) Pageable pageable){
        return postService.findAll(pageable);
    }

    @PostMapping
    public Post create(@RequestBody PostRequest postRequest){
        var email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        var post = postRequest.toModel(user);
        return postService.create(post);

    }

}
