package com.cs411.bodywatchbackend.controller;

import com.cs411.bodywatchbackend.model.Health;
import com.cs411.bodywatchbackend.model.HealthId;
import com.cs411.bodywatchbackend.service.HealthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HealthController {

    @Autowired
    private HealthService healthService;

    @GetMapping("/health/{userId}")
    public ResponseEntity<List<Health>> getAllHealthByUserId(@PathVariable int userId){
        return ResponseEntity.ok().body(healthService.getAllHealthByUserId(userId));
    }

    @PostMapping("/health")
    public ResponseEntity<Integer> createHealth(@RequestBody Health health) {
        healthService.createHealth(health);
        return ResponseEntity.ok().body(healthService.getRanking(health.getUserId(), health.getDate()));
    }

    @PutMapping("/health")
    public HttpStatus updateHealth(@RequestBody Health health) {
        healthService.updateHealth(health);
        return HttpStatus.OK;
    }

    @DeleteMapping("/health")
    public HttpStatus deleteGoals(@RequestBody Health health){
        healthService.deleteHealth(new HealthId(health.getUserId(), health.getDate()));
        return HttpStatus.OK;
    }

}
