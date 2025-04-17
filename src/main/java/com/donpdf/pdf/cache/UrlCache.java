package com.donpdf.pdf.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlCache {
    private static final UrlCache INSTANCE = new UrlCache();
    private final Path cacheDir;
    private final Map<String, Path> urlToPathMap = new ConcurrentHashMap<>();
    private long cacheExpiryTime = 60 * 60 * 1000;
    private UrlCache() {
        try {
            this.cacheDir = Files.createTempDirectory("url-cache-");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    deleteRecursively(cacheDir);
                } catch (IOException ignored) {
                }
            }));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el caché de URL", e);
        }
    }

    public static UrlCache getInstance() {
        return INSTANCE;
    }

    public void setCacheExpiryTime(long milliseconds) {
        this.cacheExpiryTime = milliseconds;
    }

    public byte[] getResource(String urlStr) throws IOException {
        Path cachePath = getCachedFilePath(urlStr);
        if (Files.exists(cachePath)) {
            try {
                long fileTime = Files.getLastModifiedTime(cachePath).toMillis();
                if (System.currentTimeMillis() - fileTime < cacheExpiryTime) return Files.readAllBytes(cachePath);
            } catch (IOException ignored) {
            }
        }
        synchronized (urlStr.intern()) {
            if (!Files.exists(cachePath) || System.currentTimeMillis() - Files.getLastModifiedTime(cachePath).toMillis() >= cacheExpiryTime)
                downloadResource(urlStr, cachePath);
            return Files.readAllBytes(cachePath);
        }
    }

    private void downloadResource(String urlStr, Path destination) throws IOException {
        URL url = new URL(urlStr);
        try (InputStream in = url.openStream()) {
            Files.createDirectories(destination.getParent());
            Path tempFile = Files.createTempFile(destination.getParent(), "download-", ".tmp");
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            Files.move(tempFile, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private Path getCachedFilePath(String urlStr) {
        return urlToPathMap.computeIfAbsent(urlStr, url -> {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(url.getBytes());
                String hash = HexFormat.of().formatHex(hashBytes);
                String extension = ".dat";
                int lastDot = url.lastIndexOf('.');
                if (lastDot > 0 && url.length() - lastDot <= 6) extension = url.substring(lastDot);
                String subDir = hash.substring(0, 2);
                return cacheDir.resolve(subDir).resolve(hash + extension);
            } catch (NoSuchAlgorithmException e) {
                return cacheDir.resolve("url-" + url.hashCode() + ".dat");
            }
        });
    }

    public void removeFromCache(String urlStr) {
        Path path = urlToPathMap.remove(urlStr);
        if (path != null) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException ignored) {
            }
        }
    }

    public void clearCache() {
        try {
            deleteRecursively(cacheDir);
            Files.createDirectories(cacheDir);
            urlToPathMap.clear();
        } catch (IOException ignored) {
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted((p1, p2) -> -p1.compareTo(p2))
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException ignored) {
                        }
                    });
        }
    }
}
