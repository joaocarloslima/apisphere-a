package br.com.fiap.apisphere.user.dto;

import br.com.fiap.apisphere.user.User;

public record UserProfileResponse(
        String name,
        String bio,
        String email,
        String avatar
) {
    public UserProfileResponse(User user){
        this(user.getName(), user.getBio(), user.getEmail(), user.getAvatar());
    }
}
