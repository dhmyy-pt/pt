package com.ruoyi.pt.service.impl;


import com.ruoyi.pt.dao.TorrentInfo;
import com.ruoyi.pt.mapper.TorrentInfoMapper;
import com.ruoyi.pt.service.TorrentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorrentInfoServiceImpl implements TorrentInfoService {

    @Autowired
    private TorrentInfoMapper mapper;

    @Override
    public List<TorrentInfo> getAll() {
        TorrentInfo torrentInfo = mapper.selectAll().get(0);
        System.out.println(torrentInfo);
        return mapper.selectAll();
    }

    @Override
    public TorrentInfo getById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public int create(TorrentInfo info) {
        return mapper.insert(info);
    }

    @Override
    public int update(TorrentInfo info) {
        return mapper.update(info);
    }

    @Override
    public int delete(Long id) {
        return mapper.deleteById(id);
    }
}