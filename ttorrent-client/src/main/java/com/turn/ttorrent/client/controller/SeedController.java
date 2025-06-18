package com.turn.ttorrent.client.controller;


import com.turn.ttorrent.client.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
