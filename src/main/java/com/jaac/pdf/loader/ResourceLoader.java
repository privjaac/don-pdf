package com.jaac.pdf.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ResourceLoader {
    protected String resourcePath;

    protected ResourceLoader(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    protected byte[] loadResource() throws IOException {
        // 1. Intentar cargar como URL
        if (isUrl(resourcePath)) {
            return loadFromUrl(new URL(resourcePath));
        }
        // 2. Intentar cargar desde resources
        byte[] resourceData = loadFromResources();
        if (resourceData != null) {
            return resourceData;
        }
        // 3. Intentar cargar como ruta absoluta o relativa
        Path path = Paths.get(resourcePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        // 4. Intentar cargar desde la carpeta resources del proyecto
        path = Paths.get("src", "main", "resources", resourcePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        throw new IOException("No se pudo encontrar el recurso: " + resourcePath);
    }

    private boolean isUrl(String path) {
        try {
            new URL(path);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private byte[] loadFromUrl(URL url) throws IOException {
        try (InputStream in = url.openStream()) {
            return in.readAllBytes();
        }
    }

    private byte[] loadFromResources() {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (in != null) return in.readAllBytes();
        } catch (IOException ignored) {
        }
        return null;
    }
}
