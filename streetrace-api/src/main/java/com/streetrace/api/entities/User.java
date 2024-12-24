package com.streetrace.api.entities;

import com.streetrace.api.entities.car.UserCar;
import com.streetrace.api.models.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // информация пользователя
    private Long id;
    private Long telegramId;
    // TG Username
    private String username;
    private String firstName;
    private String lastName;
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
    @ManyToMany()
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends = new ArrayList<>();

    // Роль для UserDetails и Jwt logic
    @Enumerated(EnumType.STRING)
    private Role role;

    // UserDetails methods implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
