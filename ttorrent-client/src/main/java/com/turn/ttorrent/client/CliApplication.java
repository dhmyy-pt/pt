package com.turn.ttorrent.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CliApplication {
    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }
}
