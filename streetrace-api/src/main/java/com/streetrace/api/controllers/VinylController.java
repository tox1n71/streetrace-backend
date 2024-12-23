package com.streetrace.api.controllers;

import com.streetrace.api.entities.car.Vinyl;
import com.streetrace.api.services.VinylService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vinyls")
@AllArgsConstructor
public class VinylController {

    private final VinylService vinylService;

    /**
     * Получение всех стоковых винилов
     */
    @GetMapping
    public ResponseEntity<List<Vinyl>> getAllVinyls() {
        List<Vinyl> vinyls = vinylService.getAllVinyls();
        return ResponseEntity.ok(vinyls);
    }
}