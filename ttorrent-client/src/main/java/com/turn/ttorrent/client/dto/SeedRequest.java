package com.turn.ttorrent.client.dto;

import lombok.Data;

@Data
public class SeedRequest {
    private String torrentPath;
    private String sourcePath;

}