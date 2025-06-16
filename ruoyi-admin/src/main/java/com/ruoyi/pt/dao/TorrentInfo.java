package com.ruoyi.pt.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TorrentInfo {
    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    private String creator;

    private Integer seederCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastActive;

    private Integer categoryId;
}
