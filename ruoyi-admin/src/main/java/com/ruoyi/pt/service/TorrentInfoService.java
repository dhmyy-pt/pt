package com.ruoyi.pt.service;

import com.ruoyi.pt.dao.TorrentInfo;

import java.util.List;

public interface TorrentInfoService {
    List<TorrentInfo> getAll();
    TorrentInfo getById(Long id);
    int create(TorrentInfo info);
    int update(TorrentInfo info);
    int delete(Long id);
}