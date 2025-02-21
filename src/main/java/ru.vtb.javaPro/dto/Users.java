package ru.vtb.javaPro.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter

@Component
public class Users {
    private Long id;
    private String username;

    public Users(String username) {
        this.username = username;
    }

    public Users(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
