package com.example.Learn.StayEase.controller;

import com.example.Learn.StayEase.entity.Property;
import com.example.Learn.StayEase.entity.User;
import com.example.Learn.StayEase.exceptions.ApiResponse;
import com.example.Learn.StayEase.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (properties.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getEmail());
        Property propertyById = propertyService.getPropertyById(id);
        if (propertyById == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(propertyById, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@Valid @RequestBody Property property, @PathVariable Long id) {
        Property property1 = propertyService.updatePropertyById(property, id);
        if (property1 == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(property1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProperty(@PathVariable Long id) {
        propertyService.deletePropertyById(id);
        ApiResponse apiResponse = ApiResponse.builder().message("property deleted successfully for id : " + id).build();
        apiResponse.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
