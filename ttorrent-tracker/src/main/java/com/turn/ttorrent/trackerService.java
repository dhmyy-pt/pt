package com.turn.ttorrent;

import com.turn.ttorrent.tracker.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;
import java.net.InetSocketAddress;


@EnableDiscoveryClient
@SpringBootApplication
public class trackerService {

    private static final Logger logger =
            LoggerFactory.getLogger(trackerService.class);
    public static void main(String[] args) throws IOException {
        SpringApplication.run(trackerService.class);

        Tracker tracker = new Tracker(6969);

        // 替换默认 AnnounceHandler

        tracker.start(true);

        System.out.println("Tracker started on port 6969");

        logger.info("Starting tracker with {} announced torrents...");
    }
}
