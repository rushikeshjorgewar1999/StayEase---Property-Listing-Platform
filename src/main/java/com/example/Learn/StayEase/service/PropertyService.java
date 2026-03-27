package com.example.Learn.StayEase.service;

import com.example.Learn.StayEase.entity.Property;
import com.example.Learn.StayEase.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property savePropertyInDB(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> fetchAllProperties() {
        return propertyRepository.findAll().stream().toList();
    }

}
