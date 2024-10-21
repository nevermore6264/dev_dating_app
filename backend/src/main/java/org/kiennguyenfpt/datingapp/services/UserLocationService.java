package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.dtos.requests.UserLocationRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.LocationResponse;

public interface UserLocationService {

    LocationResponse getCurrentLocation(double latitude, double longitude);

    void saveLocation(UserLocationRequest userLocationRequest);

    boolean isLocationSetForUser(Long userId);

}
