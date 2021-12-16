package model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class UpdateInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    @CreationTimestamp
    @Column(name = "update_time")
    private Date updateTime;
    @ManyToOne
    private User user;
}