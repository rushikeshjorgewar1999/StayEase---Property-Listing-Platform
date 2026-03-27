package com.example.Learn.StayEase.controller;

import com.example.Learn.StayEase.entity.Property;
import com.example.Learn.StayEase.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<Property> saveProperty(@Valid @RequestBody Property property) {
        return new ResponseEntity<>(propertyService.savePropertyInDB(property), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.fetchAllProperties();
        if(properties.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(properties,HttpStatus.OK);
    }


}
