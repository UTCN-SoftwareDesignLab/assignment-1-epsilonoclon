package service.user;

import model.User;
import model.validation.Notification;

import javax.naming.AuthenticationException;

public interface AuthenticationService
{
    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password) throws AuthenticationException;

    boolean logout(User user);

    String encodePassword(String password);
}
