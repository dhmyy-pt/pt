package com.turn.ttorrent.cli.dto;

import lombok.Data;

@Data
public class SeedRequest {
    private String torrentPath;
    private String sourcePath;

}