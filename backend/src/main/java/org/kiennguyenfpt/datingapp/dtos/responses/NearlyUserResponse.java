package org.kiennguyenfpt.datingapp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NearlyUserResponse {
    private long userId;

    private String email;

    private String phone;

    private Double latitude;

    private Double longitude;

    private String address;

}
