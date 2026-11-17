package org.AnsySan.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.AnsySan.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private int idCounter;
    private final Map<Integer, User> userMap = new HashMap();
    private final Set<String> setlogin = new HashSet();
    private final AccountService accountService;

    @Autowired
    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public User createUser(String login) {
        String correctLogin = this.validateLogin(login);
        if (this.setlogin.contains(correctLogin)) {
            throw new IllegalArgumentException("User already exists with login=%s".formatted(correctLogin));
        } else {
            ++this.idCounter;
            User user = new User(this.idCounter, correctLogin, new ArrayList());
            this.accountService.createAccount(user);
            this.userMap.put(this.idCounter, user);
            this.setlogin.add(correctLogin);
            return user;
        }
    }

    private String validateLogin(String login) {
        if (login != null && !login.isBlank()) {
            return login.trim();
        } else {
            throw new IllegalArgumentException("login must not be blank");
        }
    }

    public Optional<User> findUserById(int id) {
        return Optional.ofNullable((User)this.userMap.get(id));
    }

    public List<User> findAll() {
        return this.userMap.values().stream().toList();
    }
}
