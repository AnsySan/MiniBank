package org.AnsySan.operation.commands;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import org.AnsySan.operation.ConsoleOperationType;
import org.AnsySan.operation.OperationCommand;
import org.AnsySan.user.User;
import org.AnsySan.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    @Autowired
    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    public void execute() {
        List<User> users = this.userService.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            PrintStream var10001 = System.out;
            Objects.requireNonNull(var10001);
            users.forEach(var10001::println);
        }
    }

    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}