package com.streetrace.api.services;

import com.streetrace.api.entities.car.Vinyl;
import com.streetrace.api.repos.VinylRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VinylService {

    private final VinylRepository vinylRepository;

    public List<Vinyl> getAllVinyls() {
        return vinylRepository.findAll();
    }
}
