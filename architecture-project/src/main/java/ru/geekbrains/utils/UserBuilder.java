package ru.geekbrains.utils;
import ru.geekbrains.entities.User;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder setName(String name){
        user.setName(name);
        return this;
    }

    public UserBuilder setUserName(String userName){
        user.setUsername(userName);
        return this;
    }

    public UserBuilder setPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder setEmail(String email){
        user.setEmail(email);
        return this;
    }

    public UserBuilder setPhone(String phone){
        user.setPhone(phone);
        return this;
    }

    public User build(){
        return user;
    }
}
