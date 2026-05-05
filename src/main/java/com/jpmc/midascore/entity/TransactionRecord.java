package com.jpmc.midascore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder    
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private float incentive;
    
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;
}
