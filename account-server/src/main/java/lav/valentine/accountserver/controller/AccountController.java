package lav.valentine.accountserver.controller;

import lav.valentine.accountserver.dto.ResponseDto;
import lav.valentine.accountserver.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}/amount")
    public ResponseDto getAmount(@PathVariable Integer id) {
        return new ResponseDto(String.format("The balance of account %d is equal to %d", id, accountService.getAmount(id)));
    }

    @PostMapping("/{id}/amount")
    public ResponseDto addAmount(@PathVariable Integer id, @RequestParam Long value) {
        accountService.addAmount(id, value);
        return new ResponseDto(String.format("The balance of account %d has been changed", id));
    }

    @PostMapping("/statistic")
    public ResponseDto resetStatistic() {
        return new ResponseDto("Statistics reset");
    }
}