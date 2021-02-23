package ru.geekbrains.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserData {

    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
