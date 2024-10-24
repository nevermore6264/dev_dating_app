package org.kiennguyenfpt.datingapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SubscriptionPlans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long planId;

    @Column(nullable = false)
    private String name;

    @Column
    private int duration; // Duration in days

    @Column
    private double price;

    @Column
    private boolean hasLikeLimit;

    @Column
    private boolean hasWatchLike;

    @Column
    private boolean hasShowPriority;

    @Column
    private boolean hasViewProfile;

    @Column
    private String imageUrl; // Chưa cần thiết

}
