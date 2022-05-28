package csc319.j4.bankaccountdemo.controller;

import csc319.j4.bankaccountdemo.model.BankAccount;
import csc319.j4.bankaccountdemo.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounting")
public class BankAccountController {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @PostMapping
    public boolean addAccount(@RequestBody BankAccount bankAccount) {
        this.bankAccountRepository.save(bankAccount);
        return true;
    }

    @GetMapping
    public Collection<BankAccount> getAllAccounts() {
        return this.bankAccountRepository.findAll();
    }

    @PostMapping("/deposit")
    public boolean depositToAccount(@RequestParam String accountNumber, @RequestParam double amount) {
        Optional<BankAccount> bankAccountOptional = this.bankAccountRepository.findById(accountNumber);
        if (bankAccountOptional.isEmpty()) return false;

        BankAccount bankAccount = bankAccountOptional.get();
        double newAmount = bankAccount.getAmount() + amount;
        bankAccount.setAmount(newAmount);
        this.bankAccountRepository.save(bankAccount);
        return true;
    }

    @PostMapping("/withdraw")
    public boolean withdrawFromAccount(@RequestParam String accountNumber, @RequestParam double amount) {
        Optional<BankAccount> bankAccountOptional = this.bankAccountRepository.findById(accountNumber);
        if (bankAccountOptional.isEmpty()) return false;

        BankAccount bankAccount = bankAccountOptional.get();
        double newAmount = bankAccount.getAmount() - amount;
        if (newAmount < 0) return false;

        bankAccount.setAmount(newAmount);
        this.bankAccountRepository.save(bankAccount);
        return true;
    }

    @PostMapping("/transfer")
    public boolean transferBetweenAccounts(@RequestParam String accountNumber, @RequestParam double amount, @RequestParam String toAccountNumber) {
        Optional<BankAccount> fromAccountOptional = this.bankAccountRepository.findById(accountNumber);
        Optional<BankAccount> toAccountOptional = this.bankAccountRepository.findById(toAccountNumber);
        if (fromAccountOptional.isEmpty() || toAccountOptional.isEmpty()) return false;

        BankAccount fromAccount = fromAccountOptional.get();
        BankAccount toAccount = toAccountOptional.get();

        // Check if fromAccount has enough money
        if (fromAccount.getAmount() - amount < 0) return false;

        fromAccount.setAmount(fromAccount.getAmount() - amount);
        toAccount.setAmount(toAccount.getAmount() + amount);

        this.bankAccountRepository.saveAll(List.of(fromAccount, toAccount));
        return true;
    }
}
