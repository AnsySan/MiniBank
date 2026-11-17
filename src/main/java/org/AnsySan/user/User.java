package org.AnsySan.user;

import java.util.List;
import org.AnsySan.account.Account;

public class User {
    public int id;
    public String login;
    public List<Account> accountList;

    public User(int id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public List<Account> getAccountList() {
        return this.accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public String toString() {
        int var10000 = this.id;
        return "User{id=" + var10000 + ", login='" + this.login + "', accountList=" + String.valueOf(this.accountList) + "}";
    }
}
