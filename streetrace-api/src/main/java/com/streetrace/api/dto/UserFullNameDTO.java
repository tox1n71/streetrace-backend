package com.streetrace.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserFullNameDTO {
    private String firstName;
    private String lastName;
}
