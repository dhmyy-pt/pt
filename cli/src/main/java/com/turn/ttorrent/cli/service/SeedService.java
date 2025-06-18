package com.turn.ttorrent.cli.service;

import com.turn.ttorrent.client.SimpleClient;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("seedService")
public class SeedService {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private SimpleClient client;

    public void startSeeding(String torrentPath, String fileDirectory) throws Exception {
        if (client != null) {
            throw new IllegalStateException("Seeding already in progress.");
        }

        client = new SimpleClient();
        InetAddress address = InetAddress.getLocalHost(); // 或者固定你的服务器IP
        client.downloadTorrent(torrentPath, fileDirectory, address);

        // 在后台保持运行
        executor.submit(() -> {
            try {
                while (true) {
                    Thread.sleep(500000); // 持续做种
                }
            } catch (InterruptedException ignored) {
            }
        });
    }

    public void stopSeeding() {
        if (client != null) {
            client.stop();
            client = null;
        }
    }
}
