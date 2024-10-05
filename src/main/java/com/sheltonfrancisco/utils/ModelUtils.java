package com.sheltonfrancisco.utils;

import weka.core.SerializationHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;

public class ModelUtils {

    public static <T> void saveModel(T model, String fileName) throws Exception {
        String resourcesPath = Paths.get("src", "main", "resources", "models", fileName).toString();

        try (FileOutputStream fileOutputStream = new FileOutputStream(resourcesPath)) {
            SerializationHelper.write(fileOutputStream, model);
        }
    }

    public static <T> T loadModel(String fileName, Class<T> clazz) throws Exception {
        try (InputStream inputStream = ModelUtils.class.getResourceAsStream("/models/" + fileName)) {
            if (inputStream == null)
                throw new IllegalArgumentException("File not found! " + fileName);

            return (T) SerializationHelper.read(inputStream);
        }
    }
}
