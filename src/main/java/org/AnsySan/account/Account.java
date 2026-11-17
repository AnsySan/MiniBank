package org.AnsySan.account;

public class Account {
    public int id;
    public int userId;
    public int moneyAmount;

    public Account(int id, int userId, int moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getMoneyAmount() {
        return this.moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String toString() {
        return "Account{id=" + this.id + ", userId=" + this.userId + ", moneyAmount=" + this.moneyAmount + "}";
    }
}