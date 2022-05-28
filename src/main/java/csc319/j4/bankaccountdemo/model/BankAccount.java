package csc319.j4.bankaccountdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BankAccount {
    @Id
    private String accountNumber;
    private String name;
    private String branch;
    private double amount;
}
