package org.kiennguyenfpt.datingapp.services.impl;

import org.kiennguyenfpt.datingapp.entities.Package;
import org.kiennguyenfpt.datingapp.repositories.PackageRepository;
import org.kiennguyenfpt.datingapp.services.PackageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }
}
