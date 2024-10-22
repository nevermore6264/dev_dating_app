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

    @Column(nullable = false)
    private int duration; // Duration in days

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "TEXT")
    private String features;

    @Column(nullable = true)
    private String imageUrl;
