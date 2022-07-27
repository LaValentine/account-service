package lav.valentine.accountserver.service.impl;

import lav.valentine.accountserver.entity.Account;
import lav.valentine.accountserver.entity.Transaction;
import lav.valentine.accountserver.exception.ext.AccountNotExistException;
import lav.valentine.accountserver.repository.AccountRepository;
import lav.valentine.accountserver.repository.TransactionRepository;
import lav.valentine.accountserver.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final String accountException = "";

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Long getAmount(Integer id) {
        log.info("Getting balance. Account {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotExistException(accountException));
        return account.getBalance();
    }

    @Override
    public void addAmount(Integer id, Long value) {
        log.info("Changing balance. Account {}", id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotExistException(accountException));

        transactionRepository.save(Transaction.builder()
                .account(account).value(value).time(LocalDateTime.now()).build());

        account.setBalance(account.getBalance() + value);

        accountRepository.save(account);
    }
}