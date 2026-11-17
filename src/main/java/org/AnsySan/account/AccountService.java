package org.AnsySan.account;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.AnsySan.user.User;
import org.AnsySan.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AccountService {
    private int idCounter = 0;
    private final Map<Integer, Account> mapAccounts;
    private final UserService userService;
    private final AccountProperties accountProperties;

    @Autowired
    public AccountService(@Lazy UserService userService, AccountProperties accountProperties) {
        this.userService = userService;
        this.mapAccounts = new HashMap();
        this.accountProperties = accountProperties;
    }

    public Account createAccount(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        } else {
            ++this.idCounter;
            Account account = new Account(this.idCounter, user.getId(), this.accountProperties.getDefaultAmount());
            this.mapAccounts.put(this.idCounter, account);
            user.getAccountList().add(account);
            return account;
        }
    }

    public Account createAccountForUser(int userId) {
        User user = (User)this.userService.findUserById(userId).orElseThrow(() -> new IllegalArgumentException("No such user: id=" + userId));
        return this.createAccount(user);
    }

    public Account deposit(int accountId, int amount) {
        this.validatePositiveId(accountId);
        this.validatePositiveAmount(amount);
        Account account = (Account)this.findAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("Account with id=%s not found".formatted(accountId)));
        account.setMoneyAmount(account.getMoneyAmount() + amount);
        return account;
    }

    public void withdraw(int accountId, int amount) {
        this.validatePositiveId(accountId);
        this.validatePositiveAmount(amount);
        Account account = (Account)this.findAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("Account with id=%s not found".formatted(accountId)));
        if (amount > account.getMoneyAmount()) {
            throw new IllegalArgumentException("insufficient funds on account id=%s, moneyAmount=%s, attempted withdraw=%s".formatted(account.getId(), account.getMoneyAmount(), amount));
        } else {
            account.setMoneyAmount(account.getMoneyAmount() - amount);
        }
    }

    public void transfer(int fromAccountId, int toAccountId, int amount) {
        this.validatePositiveId(fromAccountId);
        this.validatePositiveId(toAccountId);
        this.validatePositiveAmount(amount);
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("source and target account id must be different");
        } else {
            Account accountFrom = (Account)this.findAccountById(fromAccountId).orElseThrow(() -> new IllegalArgumentException("Account with id=%s not found".formatted(fromAccountId)));
            Account accountTo = (Account)this.findAccountById(toAccountId).orElseThrow(() -> new IllegalArgumentException("Account with id=%s not found".formatted(toAccountId)));
            if (amount > accountFrom.getMoneyAmount()) {
                throw new IllegalArgumentException("insufficient funds on account id=%s, moneyAmount=%s, attempted withdraw=%s".formatted(accountFrom.getId(), accountFrom.getMoneyAmount(), amount));
            } else {
                boolean sameUser = accountFrom.getUserId() == accountTo.getUserId();
                int amountToTransfer = sameUser ? amount : (int)Math.round((double)amount * ((double)1.0F - this.accountProperties.getTransferCommission()));
                int var10000 = amount - amountToTransfer;
                int sourceAfter = Math.subtractExact(accountFrom.getMoneyAmount(), amount);
                int targetAfter = Math.addExact(accountTo.getMoneyAmount(), amountToTransfer);
                accountFrom.setMoneyAmount(sourceAfter);
                accountTo.setMoneyAmount(targetAfter);
            }
        }
    }

    public Account close(int id) {
        this.validatePositiveId(id);
        Account accountClose = (Account)this.findAccountById(id).orElseThrow(() -> new IllegalArgumentException("Account with id=%s not found".formatted(id)));
        User owner = (User)this.userService.findUserById(accountClose.getUserId()).orElseThrow(() -> new IllegalStateException("Owner not found for account id=" + id));
        int userId = accountClose.getUserId();
        List<Account> userAccount = this.getUserAccounts(userId);
        if (userAccount.size() == 1) {
            throw new IllegalStateException("Can't close the only one account");
        } else {
            Account accountToTransferMoney = (Account)userAccount.stream().filter((it) -> it.getId() != id).min(Comparator.comparingInt(Account::getId)).orElseThrow(() -> new IllegalArgumentException("No target account found"));
            int newAmount = Math.addExact(accountToTransferMoney.getMoneyAmount(), accountClose.getMoneyAmount());
            accountToTransferMoney.setMoneyAmount(newAmount);
            this.mapAccounts.remove(id);
            owner.getAccountList().removeIf((account) -> account.getId() == id);
            return accountClose;
        }
    }

    public Optional<Account> findAccountById(Integer idAccount) {
        this.validatePositiveId(idAccount);
        return Optional.ofNullable((Account)this.mapAccounts.get(idAccount));
    }

    private void validatePositiveId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("must be > 0");
        }
    }

    private void validatePositiveAmount(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
    }

    public List<Account> getUserAccounts(Integer userId) {
        return this.mapAccounts.values().stream().filter((it) -> userId.equals(it.getUserId())).toList();
    }

    public double getTransferCommission() {
        return this.accountProperties.getTransferCommission();
    }
}