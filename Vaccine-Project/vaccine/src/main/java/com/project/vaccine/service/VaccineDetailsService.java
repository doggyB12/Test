package com.project.vaccine.service;

import com.project.vaccine.entity.VaccineDetails;
import com.project.vaccine.repository.VaccineDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineDetailsService {
    @Autowired
    VaccineDetailsRepository vaccineDetailsRepository;

    public List<VaccineDetails> getAllVaccineDetails() {
        return vaccineDetailsRepository.findVaccineDetailsByStatusTrue();
    }

    public VaccineDetails create(VaccineDetails vaccineDetails) {
        return vaccineDetailsRepository.save(vaccineDetails);
    }
}
