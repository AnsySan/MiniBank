package org.AnsySan.operation.commands;

import org.AnsySan.console.ConsoleInput;
import org.AnsySan.operation.ConsoleOperationType;
import org.AnsySan.operation.OperationCommand;
import org.AnsySan.user.User;
import org.AnsySan.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserCommand implements OperationCommand {
    private UserService userService;
    private ConsoleInput consoleInput;

    public CreateUserCommand(UserService userService, ConsoleInput consoleInput) {
        this.userService = userService;
        this.consoleInput = consoleInput;
    }

    public void execute() {
        String login = this.consoleInput.readRequiredString("Enter login:");
        User user = this.userService.createUser(login);
        System.out.println("User created: " + String.valueOf(user));
    }

    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}