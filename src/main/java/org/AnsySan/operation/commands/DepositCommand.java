package org.AnsySan.operation.commands;

import org.AnsySan.account.Account;
import org.AnsySan.account.AccountService;
import org.AnsySan.console.ConsoleInput;
import org.AnsySan.operation.ConsoleOperationType;
import org.AnsySan.operation.OperationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepositCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final AccountService accountService;

    @Autowired
    public DepositCommand(ConsoleInput consoleInput, AccountService accountService) {
        this.consoleInput = consoleInput;
        this.accountService = accountService;
    }

    public void execute() {
        int accountId = this.consoleInput.readRequiredInt("Enter account id:");
        int amount = this.consoleInput.readRequiredInt("Enter amount:");
        Account account = this.accountService.deposit(accountId, amount);
        System.out.println("Deposited " + amount + " to account " + account.getId() + ". New balance: " + account.getMoneyAmount());
    }

    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
