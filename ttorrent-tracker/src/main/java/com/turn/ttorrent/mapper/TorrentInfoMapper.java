package com.turn.ttorrent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turn.ttorrent.dao.TorrentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TorrentInfoMapper extends BaseMapper<TorrentInfo> {
}