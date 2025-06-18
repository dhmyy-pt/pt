package com.ruoyi.pt.service.impl;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.pt.dao.TorrentInfo;
import com.ruoyi.pt.mapper.TorrentInfoMapper;
import com.ruoyi.pt.service.TorrentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class TorrentInfoServiceImpl implements TorrentInfoService {

    @Autowired
    private TorrentInfoMapper mapper;

    private static final String UPLOAD_DIR = "G:/tmp/";

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

    @Override
    public AjaxResult Upload(String creatorId, String category, String author, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        TorrentInfo torrentInfo = mapper.selectOneByName(originalFilename);
        if (torrentInfo != null) {



            torrentInfo.setSeederCount(torrentInfo.getSeederCount() + 1);
            mapper.update(torrentInfo);
            return AjaxResult.success("上传成功,做种数+1");
        }else {
            if (file.isEmpty()) {
                return AjaxResult.error("文件上传失败");
            }else {
                File dir = new File(UPLOAD_DIR);

                if (!dir.exists()) dir.mkdirs();

                Path filePath = Paths.get(UPLOAD_DIR, originalFilename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                TorrentInfo info = new TorrentInfo();
                info.setName(originalFilename);
                info.setCategoryId(Integer.parseInt(category));
                info.setCreator(author);
                info.setCreatorId(Integer.parseInt(creatorId));
                info.setCreatedTime(java.time.LocalDateTime.now());
                info.setSeederCount(1);
                mapper.insert(info);
                return AjaxResult.success("上传成功");
            }
        }
    }

    @Override
    public ResponseEntity<?> download(Long id) throws FileNotFoundException {
        TorrentInfo torrentInfo = mapper.selectById(id);
        System.out.println(torrentInfo);
        if (torrentInfo != null) {
            String filePath = UPLOAD_DIR + torrentInfo.getName();
            File file = new File(filePath);
            System.out.println(filePath);
            if (file.exists()){
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + torrentInfo.getName() + "\"")
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("文件读取失败");
    }

    @Override
    public List<TorrentInfo> getByCategoryAndName(String category, String name) {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(category)) {
            return mapper.selectAll();
        }
        if (StringUtils.isEmpty(category)&&name!=null){
            return mapper.selectByName(name.trim());
        }else if (StringUtils.isEmpty(name)&&category!=null){
            if (category.equals("-1")){
                return mapper.selectAll();
            }
            return mapper.selectByCategory(Integer.parseInt(category));
        }else {
            if (category.equals("-1")){
                return mapper.selectByName(name.trim());
            }
            return mapper.selectByNameAndCategory(Integer.parseInt(category),name.trim());
        }
    }

    /**
     * 制作新的种子文件
     * @param creatorId
     * @param category
     * @param author
     * @return
     */
    @Override
    public ResponseEntity<?> make(String creatorId, String category, String author, String sourcePath) throws IOException {

        String outputPath = UPLOAD_DIR+ changeExtensionToTorrent(Paths.get(sourcePath).getFileName().toString());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("sourcePath", sourcePath);
        params.add("announceUrl", "http://localhost:6969/announce"); // 可按需传
        params.add("outputPath", outputPath);
        System.out.println(outputPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8085/torrent/create";
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        TorrentInfo info = new TorrentInfo();

        return ResponseEntity.ok(response.getBody());

    }


    public static String changeExtensionToTorrent(String originalName) {
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex != -1) {
            return originalName.substring(0, dotIndex) + ".torrent";
        } else {
            return originalName + ".torrent";
        }
    }
}