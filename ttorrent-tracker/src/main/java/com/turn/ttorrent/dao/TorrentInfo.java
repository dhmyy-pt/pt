package com.turn.ttorrent.dao;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("torrent_info")
public class TorrentInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("info_hash")
    private String infoHash;

    @TableField("name")
    private String name;

    @TableField("created_time")
    private LocalDateTime createdTime;

    @TableField("creator")
    private String creator;

    @TableField("seeder_count")
    private Integer seederCount;

    @TableField(value = "last_active", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastActive;
}