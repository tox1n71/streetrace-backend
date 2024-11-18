package com.streetrace.api.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TelegramAuthData {
    private Long telegramId;
    private String username;
    private String firstName;
    private String lastName;
    private String authDate;
    private String hash;
}