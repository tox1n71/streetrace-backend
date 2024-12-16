package com.streetrace.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TelegramUserDetailsService extends UserDetailsService {
    UserDetails loadUserByTelegramId(Long id);
    UserDetails loadUserById(Long id);
}
