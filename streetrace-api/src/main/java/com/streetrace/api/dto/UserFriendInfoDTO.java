package com.streetrace.api.dto;

import com.streetrace.api.dto.car.UserCarDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserFriendInfoDTO {
    // айди
    private Long id;
    // айди в телеге
    private Long telegramId;
    // имя
    private String firstName;
    // фамилия
    private String lastName;
    // гонок выиграно
    private int winsCount;
    // гонок проиграно
    private int losesCount;
    // уровень
    private int level;
    // тачка текущая
    private UserCarDTO currentCar;
}