package org.kiennguyenfpt.datingapp.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kiennguyenfpt.datingapp.dtos.requests.UserLocationRequest;
import org.kiennguyenfpt.datingapp.dtos.responses.LocationResponse;
import org.kiennguyenfpt.datingapp.entities.User;
import org.kiennguyenfpt.datingapp.entities.UserLocation;
import org.kiennguyenfpt.datingapp.repositories.LocationRepository;
import org.kiennguyenfpt.datingapp.repositories.UserRepository;
import org.kiennguyenfpt.datingapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationServiceImpl implements LocationService {

    private final RestTemplate restTemplate;

    private final UserRepository userRepository;

    public LocationServiceImpl(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public LocationResponse getCurrentLocation(double latitude, double longitude) {
        String apiKey = "d9a3940071bf49f292a2b88432274bbb";
        String url = String.format("https://api.geoapify.com/v2/place-details?lat=%f&lon=%f&apiKey=%s", latitude, longitude, apiKey);

        // Thực hiện yêu cầu GET và nhận kết quả
        String response = restTemplate.getForObject(url, String.class);

        try {
            return parseLocationResponse(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private LocationRepository locationRepository;

    public void saveLocation(UserLocationRequest userLocationRequest) {
        // Create a new UserLocation entity
        UserLocation userLocation = new UserLocation();

        // Map fields from the request to the entity
        userLocation.setLatitude(userLocationRequest.getLatitude());
        userLocation.setLongitude(userLocationRequest.getLongitude());
        userLocation.setWard(userLocationRequest.getWard());
        userLocation.setDistrict(userLocationRequest.getDistrict());
        userLocation.setProvince(userLocationRequest.getProvince());

        // Assuming you have a way to get the User entity from the userId
        User user = userRepository.findById(userLocationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userLocation.setUser(user); // Set the User entity

        // Save the mapped entity to the database
        locationRepository.save(userLocation);
    }

    public LocationResponse parseLocationResponse(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // Truy cập vào node features
        JsonNode featuresNode = rootNode.path("features").get(0);
        JsonNode propertiesNode = featuresNode.path("properties");

        // Lấy thông tin cần thiết
        String name = propertiesNode.path("name").asText();
        String city = propertiesNode.path("city").asText();
        String country = propertiesNode.path("country").asText();
        double latitude = propertiesNode.path("lat").asDouble();
        double longitude = propertiesNode.path("lon").asDouble();
        String formatted = propertiesNode.path("formatted").asText();

        return new LocationResponse(name, city, country, latitude, longitude, formatted);
    }
}
