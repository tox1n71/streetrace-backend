package com.streetrace.api.entities.car;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Setter
@Getter
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int price;
    private int power; // лошадиные силы
    private int level;
    private String defaultColor;
}
