//package com.streetrace.api.mappers;
//
//import com.streetrace.api.dto.car.UserCarDTO;
//import com.streetrace.api.dto.car.UserCarVinylDTO;
//import com.streetrace.api.entities.car.UserCar;
//
//import java.util.stream.Collectors;
//
//public class UserMapper {
//    public static UserCarDTO convertToUserCarDTO(UserCar userCar) {
//        return UserCarDTO.builder()
//                .id(userCar.getId())
//                .model(userCar.getModel())
//                .power(userCar.getPower())
//                .color(userCar.getColor())
//                .userCarVinyls(userCar.getUserCarVinyls().stream()
//                        .map(vinyl -> UserCarVinylDTO.builder()
//                                .id(vinyl.getId())
//                                .name(vinyl.getVinyl().getName())
//                                .x(vinyl.getX())
//                                .y(vinyl.getY())
//                                .rotationAngle(vinyl.getRotationAngle())
//                                .build()
//                        ).collect(Collectors.toList()))
//                .build();
//    }
//}
