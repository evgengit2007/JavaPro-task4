package ru.vtb.javaPro.dto;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter

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
