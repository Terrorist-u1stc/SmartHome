package com.smarthome.AIHome.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUtils {
    private String path;

    public void setPath(String path) {
        this.path = path;
    }
    public String saveImage(byte[] imageData) throws IOException {

        String fileName = UUID.randomUUID().toString().replace("-", "") + ".png";
        Path filePath = Paths.get(path + "/" + fileName);

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(imageData);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return filePath.toString();
    }
}
