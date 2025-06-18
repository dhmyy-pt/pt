package com.ruoyi.pt.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.pt.dao.TorrentInfo;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface TorrentInfoService {
    List<TorrentInfo> getAll();
    TorrentInfo getById(Long id);
    int create(TorrentInfo info);
    int update(TorrentInfo info);
    int delete(Long id);

    AjaxResult Upload(String creatorId, String category, String author, MultipartFile file) throws IOException;

    ResponseEntity<?> download(Long id) throws FileNotFoundException;

    List<TorrentInfo> getByCategoryAndName(String category, String name);
    ;

    ResponseEntity<?> make(String creatorId, String category, String author, String sourcePath) throws IOException;
}