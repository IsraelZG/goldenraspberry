package com.igianesini.goldenraspberry.controller;

import com.igianesini.goldenraspberry.dto.AwardIntervalResponseDTO;
import com.igianesini.goldenraspberry.services.AwardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin
@RestController
@RequestMapping(value="/api/producers")
public class ProducerController {

    @Autowired
    private AwardService awardService;

    @GetMapping("/award-intervals")
    public ResponseEntity<AwardIntervalResponseDTO> getAwardIntervals() {
        AwardIntervalResponseDTO intervals = awardService.getAwardIntervals();
        return ResponseEntity.ok().body(intervals);
    }
}
