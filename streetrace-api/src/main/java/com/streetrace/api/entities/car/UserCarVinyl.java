package com.streetrace.api.entities.car;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCarVinyl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    @ManyToOne
    @JoinColumn(name = "vinyl_id")
    private Vinyl vinyl;

    private int x; // Координата X
    private int y; // Координата Y
    private float rotationAngle; // Угол поворота винила
}
