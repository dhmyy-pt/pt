package com.turn.ttorrent.client.dto;

import lombok.Data;

@Data
public class StopSeedRequest {
    private String torrentFileName;
}
