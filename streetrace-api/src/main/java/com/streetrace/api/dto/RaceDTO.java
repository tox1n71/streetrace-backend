package com.streetrace.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RaceDTO {
    private String friendName;
    private boolean userWon;
}
