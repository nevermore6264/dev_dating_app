package org.kiennguyenfpt.datingapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kiennguyenfpt.datingapp.entities.Cafe;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CafeResponse {
    private long cafeId;
    private String name;
    private String address;
    private String bio;
    private double priceRangeMin;
    private double priceRangeMax;
    private String imageUrl;

    public CafeResponse(Cafe cafe) {
        this.cafeId = cafe.getCafeId();
        this.name = cafe.getName();
        this.address = cafe.getAddress();
        this.bio = cafe.getBio();
        this.priceRangeMin = cafe.getPriceRangeMin();
        this.priceRangeMax = cafe.getPriceRangeMax();
        this.imageUrl = cafe.getImageUrl();
    }
}
