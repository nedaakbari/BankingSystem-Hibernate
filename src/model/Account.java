package model;

import enums.AccountType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(updatable = false)
    private Long accNumber;
    private Long cardNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accType;
    private double balance;
    private int cvv2;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date createAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exp_date")
    private Date expireDate;

    @ManyToOne
    @JoinColumn(name = "userId_fk")
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<TransAction> transActions = new ArrayList<>();
    @Transient
    private final int countTransaction = 3;


    public Date generateExpireDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 4);
        return cal.getTime();
    }

    @Builder(setterPrefix = "with")
    public Account(AccountType accountType, User user, double balance) {
        long leftLimit = 100000000000000L;
        long rightLimit = 1000000000000000L;
        this.accNumber = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

        long left = 100000000000000L;
        long right = 1000000000000000L;
        this.cardNumber = left + (long) (Math.random() * (right - left));

        this.accType = accountType;
        this.balance = balance;
        this.expireDate = generateExpireDate();

        int min = 1000;
        int max = 10000;
        this.cvv2 = (int) ((Math.random() * (max - min)) + min);
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accNumber=" + accNumber +
                ", cardNumber=" + cardNumber +
                ", accType=" + accType +
                ", balance=" + balance +
                ", cvv2=" + cvv2 +
                ", createAccount=" + createAccount +
                ", expireDate=" + expireDate +
                ", user=" + user +
                '}';
    }
}
