package model;

import enums.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"nationalCode"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String family;

    @Column(updatable = false, nullable = false)
    private String nationalCode;

    //@Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message="Please enter valid Email")
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @CreationTimestamp
    private Date registerDate;
    @UpdateTimestamp
    private Date lastUpdateDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UpdateInfo> updates = new ArrayList<>();

    @Builder(setterPrefix = "with")
    public User(String name, String family, String nationalCode, String email) {
        this.name = name;
        this.family = family;
        this.nationalCode = nationalCode;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", registerDate=" + registerDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", accounts=" + accounts +
                '}';
    }
}
