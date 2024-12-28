package edu.ups.minibank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "T_BANK_ACCOUNTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACC_ID", nullable = false)
    private int id;

    @Column(name = "ACC_OWNER")
    private String accountOwnerName;

    @Column(name = "ACC_TYPE", nullable = false)
    private String accountType;

    @Column(name = "ACC_AMOUNT", nullable = false)
    private BigDecimal amount;
}
