package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.dtos.requests.CafeRequest; // Thêm import
import org.kiennguyenfpt.datingapp.dtos.responses.CafeResponse; // Thêm import
import org.kiennguyenfpt.datingapp.entities.Cafe;
import org.kiennguyenfpt.datingapp.repositories.CafeRepository;
import org.kiennguyenfpt.datingapp.services.CafeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CafeServiceImpl implements CafeService {
    private final CafeRepository cafeRepository;

    public CafeServiceImpl(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @Override
    public CafeResponse createCafe(CafeRequest cafeRequest) {
        Cafe cafe = new Cafe();
        cafe.setName(cafeRequest.getName());
        cafe.setAddress(cafeRequest.getAddress());
        cafe.setBio(cafeRequest.getBio());
        cafe.setPriceRangeMin(cafeRequest.getPriceRangeMin());
        cafe.setPriceRangeMax(cafeRequest.getPriceRangeMax());
        cafe.setImageUrl(cafeRequest.getImageUrl());
        Cafe savedCafe = cafeRepository.save(cafe);
        return new CafeResponse(savedCafe.getCafeId(), savedCafe.getName(), savedCafe.getAddress(),
                savedCafe.getBio(), savedCafe.getPriceRangeMin(), savedCafe.getPriceRangeMax(), savedCafe.getImageUrl());
    }

    @Override
    public CafeResponse updateCafe(Long id, CafeRequest cafeRequest) {
        Optional<Cafe> existingCafe = cafeRepository.findById(id);
        if (existingCafe.isPresent()) {
            Cafe updatedCafe = existingCafe.get();
            updatedCafe.setName(cafeRequest.getName());
            updatedCafe.setAddress(cafeRequest.getAddress());
            updatedCafe.setBio(cafeRequest.getBio());
            updatedCafe.setPriceRangeMin(cafeRequest.getPriceRangeMin());
            updatedCafe.setPriceRangeMax(cafeRequest.getPriceRangeMax());
            updatedCafe.setImageUrl(cafeRequest.getImageUrl());
            Cafe savedCafe = cafeRepository.save(updatedCafe);
            return new CafeResponse(savedCafe.getCafeId(), savedCafe.getName(), savedCafe.getAddress(),
                    savedCafe.getBio(), savedCafe.getPriceRangeMin(), savedCafe.getPriceRangeMax(), savedCafe.getImageUrl());
        } else {
            throw new RuntimeException("Cafe not found");
        }
    }

    @Override
    public String deleteCafe(Long id) {
        cafeRepository.deleteById(id);
        return "You have deleted successfully"; // Trả về thông điệp
    }

    @Override
    public List<CafeResponse> getAllCafes() {
        return cafeRepository.findAll().stream().map(cafe -> new CafeResponse(
                cafe.getCafeId(), cafe.getName(), cafe.getAddress(), cafe.getBio(),
                cafe.getPriceRangeMin(), cafe.getPriceRangeMax(), cafe.getImageUrl())).collect(Collectors.toList());
    }

    @Override
    public CafeResponse getCafeById(Long id) {
        Cafe cafe = cafeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafe not found"));
        return new CafeResponse(cafe.getCafeId(), cafe.getName(), cafe.getAddress(),
                cafe.getBio(), cafe.getPriceRangeMin(), cafe.getPriceRangeMax(), cafe.getImageUrl());
    }
//    public CafeResponse getCafeById(Long id) {
//        Cafe cafe = cafeRepository.findById(id)
//                .orElseThrow(() -> new CafeNotFoundException("Cafe not found")); // Sử dụng exception mới
//        return new CafeResponse(cafe);
//    }
//    public Cafe getCafeById(Long id) {
//        return cafeRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cafe not found"));
//    }
}
