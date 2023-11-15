package com.mindhub.HomeBanking2.service.implement;

import com.mindhub.HomeBanking2.dto.AccountDTO;
import com.mindhub.HomeBanking2.models.Account;
import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.AccountRepository;
import com.mindhub.HomeBanking2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountRepository accountRepository;


    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> getAllAccountsDTO() {
        return getAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }



    @Override
    public Set<Account> getAllAccountsByClient(Client client) {
        return accountRepository.findByClient(client);
    }


    @Override
    public AccountDTO getAccountDTOById(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }


    @Override
    public Set<AccountDTO> getAllAccountsDTOByClient(Client client) {
        return getAllAccountsByClient(client).stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    @Override
    public Account findAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }


    @Override
    public boolean existsAccountByNumber(String string) {
        return accountRepository.existsByNumber(string);
    }


    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

}
