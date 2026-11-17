package org.AnsySan.operation.commands;

import org.AnsySan.account.Account;
import org.AnsySan.account.AccountService;
import org.AnsySan.console.ConsoleInput;
import org.AnsySan.operation.ConsoleOperationType;
import org.AnsySan.operation.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final AccountService accountService;

    public CreateAccountCommand(ConsoleInput consoleInput, AccountService accountService) {
        this.consoleInput = consoleInput;
        this.accountService = accountService;
    }

    public void execute() {
        int userId = this.consoleInput.readRequiredInt("Enter user id:");
        Account account = this.accountService.createAccountForUser(userId);
        System.out.println("Account created: " + String.valueOf(account));
    }

    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}