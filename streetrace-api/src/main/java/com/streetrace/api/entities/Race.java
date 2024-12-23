package com.streetrace.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    private int userCarHorsepower;  // Лошадиные силы машины пользователя
    private int friendCarHorsepower;  // Лошадиные силы машины друга
    private boolean userWon;  // Результат гонки (победа/поражение)(а еще может exception лютый тип нет бензина)
    private LocalDateTime raceTime;
}