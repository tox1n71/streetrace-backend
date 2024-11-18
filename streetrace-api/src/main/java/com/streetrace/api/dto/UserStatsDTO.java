package com.streetrace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserStatsDTO {
    // информация
    // кол-во гонок
    private int racesCount;
    // гонок выиграно
    private int winsCount;
    // гонок проиграно
    private int losesCount;
    // вместимость гаража
    private int garageCapacity;
    // денег потрачено
    private int moneySpend;
    // денег выиграно
    private int moneyWon;
    // сколько раз был вызван на гонку
    private int wasCalledCount;
    // уровень бензобака
    private int fuelTankLevel;
}