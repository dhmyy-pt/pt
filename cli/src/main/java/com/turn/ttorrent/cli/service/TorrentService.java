package com.turn.ttorrent.cli.service;

import com.turn.ttorrent.client.SimpleClient;
import com.turn.ttorrent.common.TorrentCreator;
import com.turn.ttorrent.common.TorrentMetadata;
import com.turn.ttorrent.common.TorrentSerializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.Inet4Address;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("torrentService")
public class TorrentService {

    private static final Logger logger = LoggerFactory.getLogger(TorrentService.class);


    public File createTorrent(String sourcePath, String announceUrl, String outputPath) throws Exception {
        File source = new File(sourcePath);
        if (!source.exists() || !source.canRead()) {
            throw new IllegalArgumentException("无法读取源文件或目录：" + sourcePath);
        }

        URI announceURI = new URI(announceUrl);
        List<List<URI>> announceList = new ArrayList<>();
        announceList.add(Collections.singletonList(announceURI));// 单层 announce list

        String creator = System.getProperty("user.name") + " (ttorrent)";
        int pieceLength = 512 * 1024; // 512KB

        TorrentMetadata torrent;
        if (source.isDirectory()) {
            List<File> files = new ArrayList<>(FileUtils.listFiles(source, TrueFileFilter.TRUE, TrueFileFilter.TRUE));
            Collections.sort(files);
            torrent = TorrentCreator.create(source, files, announceURI, announceList, creator, pieceLength);
        } else {
            torrent = TorrentCreator.create(source, null, announceURI, announceList, creator, pieceLength);
        }

        File outFile = new File(outputPath);
        byte[] data = new TorrentSerializer().serialize(torrent);
        FileUtils.writeByteArrayToFile(outFile, data);

        return outFile;
    }

    public String downloadTorrent(String torrentFilePath, String outputDirPath) {
        try {
            SimpleClient client = new SimpleClient();

            File torrentFile = new File(torrentFilePath);
            File outputDir = new File(outputDirPath);

            if (!torrentFile.exists()) {
                return "种子文件不存在";
            }

            if (!outputDir.exists()) {
                boolean created = outputDir.mkdirs();
                if (!created) {
                    return "无法创建输出目录";
                }
            }

            Inet4Address localhost = (Inet4Address) Inet4Address.getLocalHost();
            client.downloadTorrent(torrentFile.getAbsolutePath(), outputDir.getAbsolutePath(), localhost);

            logger.info("已开始下载: {}", torrentFile.getName());
            return "下载成功";
        }catch (Exception e) {
            logger.error("下载失败", e);
            return "下载失败: " + e.getMessage();
        }
    }
}
