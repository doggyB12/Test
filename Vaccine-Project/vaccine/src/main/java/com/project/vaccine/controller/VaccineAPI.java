package com.project.vaccine.controller;

import com.project.vaccine.entity.Vaccine;
import com.project.vaccine.service.VaccineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/vaccine")
public class VaccineAPI {
    @Autowired
    VaccineService vaccineService;

    @GetMapping
    public ResponseEntity getAllVaccine() {
        return ResponseEntity.ok(vaccineService.getAllVaccine());
    }

    @PostMapping
    public ResponseEntity createVaccine(@Valid @RequestBody Vaccine vaccine) {
        Vaccine newVaccine = vaccineService.create(vaccine);
        return ResponseEntity.ok(newVaccine);
    }
}
