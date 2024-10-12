package org.kiennguyenfpt.datingapp.services;

import org.kiennguyenfpt.datingapp.dtos.responses.LocationResponse;

public interface LocationService {

    LocationResponse getCurrentLocation(double latitude, double longitude);
}
