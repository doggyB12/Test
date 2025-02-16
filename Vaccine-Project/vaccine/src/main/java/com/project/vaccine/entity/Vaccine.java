package com.project.vaccine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private boolean status = true;

    @Min(value = 0,message = "Quantity must be at least 0")
    private long quantity;

    private LocalDateTime create_At;

    private LocalDateTime update_At;

    @Min(value = 0,message = "Age must be at least 0 months age")
    @Max(value = 72,message = "Age must be at most 72 months age")
    private int min_age;

    @Min(value = 0,message = "Age must be at least 0 months age")
    @Max(value = 72,message = "Age must be at most 72 months age")
    private int max_age;


    @OneToMany(mappedBy = "vaccine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VaccineDetails> vaccineDetails = new ArrayList<>();
}
