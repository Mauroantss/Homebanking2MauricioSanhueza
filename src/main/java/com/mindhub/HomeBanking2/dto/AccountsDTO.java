package com.mindhub.HomeBanking2.dto;

import com.mindhub.HomeBanking2.models.Account;

public class AccountsDTO {

    private Long Id;

    private String Number;

    private int Balance;

    public AccountsDTO(Account account){

        Id = account.getId();
        Number = account.getNumber();
        Balance = account.getBalance();
    }

    public Long getId() {
        return Id;
    }

    public String getNumber() {
        return Number;
    }

    public int getBalance() {
        return Balance;
    }
}
