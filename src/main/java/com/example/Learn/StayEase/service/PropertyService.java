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

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new RuntimeException("property not found for id : " + id));
    }

    public Property updatePropertyById(Property property, Long id) {
        Property propertyById = getPropertyById(id);
        if(propertyById != null) {
            property.setPropertyId(id);
            return propertyRepository.save(property);
        }
        return null;
    }

    public void deletePropertyById(Long id) {
         propertyRepository.deleteById(id);
    }

}
