package com.airbnb.controller;

import com.airbnb.entity.Country;
import com.airbnb.entity.Property;
import com.airbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/{locCounName}")
    public ResponseEntity<List<Property>> findProperty(@PathVariable String locCounName){

        List<Property> properties = propertyRepository.findPropertyByLocationOrCountry(locCounName);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }




}
