package com.turn.ttorrent.cli.dto;

import lombok.Data;

@Data
public class StopSeedRequest {
    private String torrentFileName;
}
