package com.markethub.smarkethubback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.markethub.smarkethubback.model.Account;
import com.markethub.smarkethubback.service.IAccountService;
import com.markethub.smarkethubback.service.LoginObject;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account savedAccount = accountService.save(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        account.setIdAccount(id);
        Account updatedAccount = accountService.update(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody LoginObject loginObject) {
        Account loggedInAccount = accountService.login(loginObject);
        return loggedInAccount != null ?
                ResponseEntity.ok(loggedInAccount) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


}
