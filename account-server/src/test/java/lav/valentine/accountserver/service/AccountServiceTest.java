package lav.valentine.accountserver.service;

import lav.valentine.accountserver.entity.Account;
import lav.valentine.accountserver.exception.ext.AccountNotExistException;
import lav.valentine.accountserver.repository.AccountRepository;
import lav.valentine.accountserver.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountService;

    @Test
    void getAmount() {
        Account account = new Account(0, 0L);

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        Long amount = accountService.getAmount(account.getId());

        assertNotNull(amount);
        assertEquals(amount, account.getBalance());
    }

    @Test
    void getAmountWhenAccountNotExistException() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AccountNotExistException.class, () -> accountService.getAmount(0));
    }

    @Test
    void addAmountWhenAccountNotExistException() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AccountNotExistException.class, () -> accountService.addAmount(0, 0L));
    }
}