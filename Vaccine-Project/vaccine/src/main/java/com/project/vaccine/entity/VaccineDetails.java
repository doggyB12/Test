package com.project.vaccine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1,message = "Dose number must be at least 1")
    private int dose_number;

    private LocalDateTime date_after;

    private  boolean status = true;

    private LocalDateTime create_At;

    private LocalDateTime update_At;


    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable = false, referencedColumnName = "id")
    private Vaccine vaccine;

    @Min(value = 0,message = "Price must be at least 0")
    private double price;
}
