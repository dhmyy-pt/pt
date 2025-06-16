package com.ruoyi.pt.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.pt.dao.TorrentInfo;
import com.ruoyi.pt.service.TorrentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/torrent")
public class TorrentInfoController {

    @Autowired
    private TorrentInfoService service;

    @GetMapping("/list")
    public AjaxResult list() {
        List<TorrentInfo> list = service.getAll();
        return AjaxResult.success(list);
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody TorrentInfo info) {
        return AjaxResult.success(service.create(info));
    }

    @GetMapping("/{id}")
    public AjaxResult get(@PathVariable Long id) {
        return AjaxResult.success(service.getById(id));
    }

    @PutMapping
    public AjaxResult update(@RequestBody TorrentInfo info) {
        return AjaxResult.success(service.update(info));
    }

    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        return AjaxResult.success(service.delete(id));
    }
}