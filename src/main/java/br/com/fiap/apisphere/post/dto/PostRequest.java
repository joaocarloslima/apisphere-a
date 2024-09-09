package br.com.fiap.apisphere.post.dto;

import br.com.fiap.apisphere.post.Post;
import br.com.fiap.apisphere.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

public record PostRequest(String text) {
    public Post toModel(User user) {
        return Post.builder()
                .text(text)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }

}
