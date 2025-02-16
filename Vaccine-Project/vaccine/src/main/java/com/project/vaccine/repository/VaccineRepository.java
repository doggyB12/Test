package com.project.vaccine.repository;

import com.project.vaccine.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findVaccinesByStatusTrue();
}
