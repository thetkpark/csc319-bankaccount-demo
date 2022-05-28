package csc319.j4.bankaccountdemo.repository;

import csc319.j4.bankaccountdemo.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {
    List<BankAccount> findAll(); // To force the implementation to return List<BankAccount>
}
