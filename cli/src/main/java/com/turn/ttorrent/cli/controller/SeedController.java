package com.turn.ttorrent.cli.controller;

import com.turn.ttorrent.cli.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private SeedService seedService;

    @PostMapping("/start")
    public String startSeed(
            @RequestParam String torrentPath,
            @RequestParam String fileDirectory) {
        try {
            seedService.startSeeding(torrentPath, fileDirectory);
            return "Seeding started successfully.";
        } catch (Exception e) {
            return "Failed to start seeding: " + e.getMessage();
        }
    }

    @PostMapping("/stop")
    public String stopSeed() {
        seedService.stopSeeding();
        return "Seeding stopped.";
    }
}
