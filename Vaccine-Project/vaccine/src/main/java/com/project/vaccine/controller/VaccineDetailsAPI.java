package com.project.vaccine.controller;

import com.project.vaccine.entity.VaccineDetails;
import com.project.vaccine.service.VaccineDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vaccine-details")
public class VaccineDetailsAPI {
    @Autowired
    VaccineDetailsService vaccineDetailsService;

    @GetMapping
    public ResponseEntity getAllVaccineDetails() {
        return ResponseEntity.ok(vaccineDetailsService.getAllVaccineDetails());
    }

    @PostMapping
    public ResponseEntity createVaccineDetails(@Valid @RequestBody VaccineDetails vaccineDetails) {
        VaccineDetails newVaccineDetails = vaccineDetailsService.create(vaccineDetails);
        return ResponseEntity.ok(newVaccineDetails);
    }
}
