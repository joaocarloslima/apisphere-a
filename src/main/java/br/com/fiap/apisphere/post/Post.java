package br.com.fiap.apisphere.post;

import br.com.fiap.apisphere.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    LocalDateTime createdAt;

    @ManyToOne
    User user;

}
