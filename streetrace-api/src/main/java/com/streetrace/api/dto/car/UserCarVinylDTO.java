package com.streetrace.api.dto.car;

import com.streetrace.api.entities.car.Vinyl;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCarVinylDTO {
    private Long id;
    private String name;
    private int x; // Координата X
    private int y; // Координата Y
    private float rotationAngle; // Угол поворота винила
}
