package com.turn.ttorrent.cli.controller;

import com.turn.ttorrent.cli.dto.SeedRequest;
import com.turn.ttorrent.cli.dto.StopSeedRequest;
import com.turn.ttorrent.cli.service.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/torrent")
public class TorrentController {

    @Autowired
    private TorrentService torrentService;
    /**
     * 创建种子文件
     * @param sourcePath 文件或目录路径
     * @param announceUrl tracker 服务器地址
     * @return 生成的 .torrent 文件路径
     */
    @PostMapping("/create")
    public ResponseEntity<String> createTorrent(
            @RequestParam String sourcePath,
            @RequestParam String announceUrl,
            @RequestParam(defaultValue = "/tmp/generated.torrent") String outputPath
    ) {
        try {
            File torrentFile = torrentService.createTorrent(sourcePath, announceUrl, outputPath);
            System.out.println(sourcePath);
            System.out.println(announceUrl);
            System.out.println(outputPath);
            return ResponseEntity.ok("种子创建成功：" + torrentFile.getAbsolutePath());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("种子创建失败: " + e.getMessage());
        }
    }
    @PostMapping("/download")
    public String download(@RequestParam("torrentFile") String torrentFile,
                           @RequestParam("outputDir") String outputDir) {
        try {
            // 启动下载
            return torrentService.downloadTorrent(torrentFile, outputDir);
        } catch (Exception e) {
            e.printStackTrace();
            return "服务异常：" + e.getMessage();
        }
    }



}
