package com.streetrace.api.controllers;

import com.streetrace.api.exceptions.NotEnoughFuelException;
import com.streetrace.api.services.RaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/race")
public class RaceController {
    private final RaceService raceService;
    @PostMapping
    // пожалуйста не забудьте что здесь carId относится к пользователю!!!!! У друга последнюю достаем, он не в приоритете
    public ResponseEntity race(@RequestHeader("Authorization") String jwtToken, @RequestParam Long carId,@RequestParam Long friendId) {
        try{
            Boolean race = raceService.race(jwtToken.substring(7), carId, friendId);
            return ResponseEntity.ok(race);
        }
        catch (NotEnoughFuelException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-last-four-races")
    public ResponseEntity getLastFourRaces(@RequestHeader("Authorization") String jwtToken) {
            return ResponseEntity.ok(raceService.getLastRacesForUser(jwtToken.substring(7)));
    }
}
