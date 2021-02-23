package ru.geekbrains.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class UserAdminData {

    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private List<String> roles;
    private String email;
    private String phone;

}
