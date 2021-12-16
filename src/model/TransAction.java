package model;

import enums.TransActionType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class TransAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date date;
    private double amount;

    @Column(name = "TRANSACTION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransActionType type;

    @ManyToOne//cascade = CascadeType.ALL
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Builder(setterPrefix = "with")
    public TransAction(double amount, TransActionType type, Account account) {
        this.amount = amount;
        this.type = type;
        this.account = account;
    }

}
