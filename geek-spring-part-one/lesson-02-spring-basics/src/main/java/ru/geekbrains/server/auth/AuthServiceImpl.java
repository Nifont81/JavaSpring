package ru.geekbrains.server.auth;

import ru.geekbrains.server.User;

import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    public Map<String, String> users = new HashMap<>();

    public AuthServiceImpl() {
        users.put("ivan", "111");
        users.put("petr", "111");
        users.put("julia", "111");
    }

    @Override
    public boolean authUser(User user) {
        String pwd = users.get(user.getLogin());
        return pwd != null && pwd.equals(user.getPassword());
    }
}
