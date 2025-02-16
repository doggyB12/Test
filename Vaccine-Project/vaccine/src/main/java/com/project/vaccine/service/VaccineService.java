package com.project.vaccine.service;

import com.project.vaccine.entity.Vaccine;
import com.project.vaccine.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {
    @Autowired
    private VaccineRepository vaccineRepository;

    public List<Vaccine> getAllVaccine() {
        return vaccineRepository.findVaccinesByStatusTrue();
    }

    public Vaccine create(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }
}
