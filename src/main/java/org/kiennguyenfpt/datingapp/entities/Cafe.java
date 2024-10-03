package org.kiennguyenfpt.datingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Cafes")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cafeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(length = 500)
    private String bio;

    @Column(nullable = false)
    private double priceRangeMin;

    @Column(nullable = false)
    private double priceRangeMax;

    private String imageUrl;
}
