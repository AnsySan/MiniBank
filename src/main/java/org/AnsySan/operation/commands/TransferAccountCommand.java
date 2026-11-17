package org.AnsySan.operation.commands;

import org.AnsySan.account.Account;
import org.AnsySan.account.AccountService;
import org.AnsySan.console.ConsoleInput;
import org.AnsySan.operation.ConsoleOperationType;
import org.AnsySan.operation.OperationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferAccountCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final AccountService accountService;

    @Autowired
    public TransferAccountCommand(ConsoleInput consoleInput, AccountService accountService) {
        this.consoleInput = consoleInput;
        this.accountService = accountService;
    }

    public void execute() {
        int fromAccountId = this.consoleInput.readRequiredInt("Enter source account id:");
        int toAccountId = this.consoleInput.readRequiredInt("Enter target account id:");
        int amount = this.consoleInput.readRequiredInt("Enter amount:");
        Account accountFrom = (Account)this.accountService.findAccountById(fromAccountId).orElseThrow(() -> new IllegalArgumentException("Account with id=" + fromAccountId + " not found"));
        Account accountTo = (Account)this.accountService.findAccountById(toAccountId).orElseThrow(() -> new IllegalArgumentException("Account with id=" + toAccountId + " not found"));
        boolean sameUser = accountFrom.getUserId() == accountTo.getUserId();
        int amountToTransfer = sameUser ? amount : (int)Math.round((double)amount * ((double)1.0F - this.accountService.getTransferCommission()));
        int commission = amount - amountToTransfer;
        this.accountService.transfer(fromAccountId, toAccountId, amount);
        if (sameUser) {
            System.out.println("Transfer completed from account " + fromAccountId + " to account " + toAccountId + ". Amount: " + amount + " (no commission, same user)");
        } else {
            System.out.println("Transfer completed from account " + fromAccountId + " to account " + toAccountId + ". Amount: " + amount + ", commission: " + commission + ", recipient received: " + amountToTransfer);
        }

    }

    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}