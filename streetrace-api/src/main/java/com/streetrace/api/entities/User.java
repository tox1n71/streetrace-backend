package com.streetrace.api.entities;

import com.streetrace.api.entities.car.UserCar;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // информация пользователя
    private Long id;
    private Long telegramId;
    private String username;
    private String firstName;
    private String lastName;
    private String telegramUsername;
    // машинки
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserCar> userCars;
    @ManyToOne
    @JoinColumn(name = "current_car_id")
    private UserCar currentCar;  // Текущая машина пользователя
    // ресурсы
    private int money;
    private int fuel;
    // уровень
    private int level;
    // прокачка аккаунта 😈
    private int fuelTankLevel;
    private int garageCapacity;
    // статистика
    private int racesCount;
    private int winsCount;
    private int losesCount;
    private int wasCalledCount;
    private int moneySpend;
    private int moneyWon;
    // друзьяшки (the boys)
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends = new ArrayList<>();
}
