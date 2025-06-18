package com.ruoyi.pt.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.pt.dao.TorrentInfo;
import com.ruoyi.pt.service.TorrentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/torrent")
public class TorrentInfoController {

    @Autowired
    private TorrentInfoService service;

    @GetMapping("/list")
    public AjaxResult list(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String name) {
        List<TorrentInfo> list = service.getByCategoryAndName(category,name);
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

    @PostMapping("/upload")
    public AjaxResult upload(@RequestParam("creatorId") String creatorId,
                             @RequestParam("author") String author,
                             @RequestParam("category") String category,
                             @RequestParam("file") MultipartFile file) throws IOException {

        return service.Upload(creatorId, category, author, file);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) throws FileNotFoundException {
        return service.download(id);
    }

    @PostMapping("/make")
    public ResponseEntity<?> make(@RequestParam("creatorId") String creatorId,
                                  @RequestParam("author") String author,
                                  @RequestParam("category") String category,
                                  @RequestParam("sourcePath") String sourcePath) throws IOException {
        return service.make(creatorId, category, author, sourcePath);
    }

}