package com.project.vaccine.repository;

import com.project.vaccine.entity.VaccineDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineDetailsRepository extends JpaRepository<VaccineDetails, Long> {
    VaccineDetails findVaccineDetailsById(Long id);
    List<VaccineDetails> findVaccineDetailsByStatusTrue();
}
