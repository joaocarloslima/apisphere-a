package br.com.fiap.apisphere.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

}
