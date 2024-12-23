package com.streetrace.api.dto.car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VinylRequest {
    private Long vinylId;
    private float x;
    private float y;
    private float scale;
    private float rotationAngle;
}
