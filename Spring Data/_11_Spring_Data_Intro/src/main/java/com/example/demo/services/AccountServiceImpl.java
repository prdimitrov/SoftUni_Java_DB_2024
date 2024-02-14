package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Optional<Account> account = this.accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Account does not exist");
        }

        BigDecimal current = account.get().getBalance();

        //compareTo - -1, 0 или 1 е като по-малко, равно на или по-голямо.
        if (amount.compareTo(current) > 0) {
            throw new RuntimeException("Cannot withdraw");
        }

        account.get().setBalance(current.subtract(amount));
        this.accountRepository.save(account.get());


    }

    @Override
    public void depositMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sorry no account"));

        //много лош пример за код каза Банкин!
//        account.getBalance().add(amount);
        BigDecimal balance = account.getBalance().add(amount);
        account.setBalance(balance);

        this.accountRepository.save(account);
    }

    @Override
    @Transactional
    public void transferMoney(Long accountFrom, Long accountTo, BigDecimal amount) {
        Account fromAccount = this.accountRepository.findById(accountFrom)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account toAccount = this.accountRepository.findById(accountTo)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));


        BigDecimal fromAccountBalance = fromAccount.getBalance();
        if (fromAccountBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        BigDecimal updateFromAccountBalance = fromAccountBalance.subtract(amount);
        fromAccount.setBalance(updateFromAccountBalance);
        this.accountRepository.save(fromAccount);

        BigDecimal toAccountBalance = toAccount.getBalance();
        BigDecimal updateToAccountBalance = toAccountBalance.add(amount);
        toAccount.setBalance(updateToAccountBalance);
        accountRepository.save(toAccount);
    }
}
