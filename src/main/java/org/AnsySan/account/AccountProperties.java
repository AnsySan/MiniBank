package org.AnsySan.account;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    @Value("${account.default-amount}")
    private int defaultAmount;
    @Value("${account.transfer-commission}")
    private double transferCommission;

    public AccountProperties() {
    }

    @PostConstruct
    public void validate() {
        if (this.defaultAmount < 0) {
            throw new IllegalArgumentException("Error: account.default-amount cannot be negative.");
        } else if (Double.isNaN(this.transferCommission) || Double.isInfinite(this.transferCommission) || this.transferCommission < (double)0.0F || this.transferCommission > (double)1.0F) {
            throw new IllegalArgumentException("Error: account.transfer-commission must be a finite number between 0 and 1.");
        }
    }

    public double getTransferCommission() {
        return this.transferCommission;
    }

    public int getDefaultAmount() {
        return this.defaultAmount;
    }
}