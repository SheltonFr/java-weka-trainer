package com.sheltonfrancisco.utils;

import weka.core.SerializationHelper;

import java.io.*;
import java.nio.file.Paths;

public class ModelUtils {

    public static void cleanCSV(String filepath) {
        InputStream inputStream = ModelUtils.class.getClassLoader().getResourceAsStream(filepath);
        String resourcesPath = Paths.get("src", "main", "resources", "datasets", "output.csv").toString();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            FileWriter fw = new FileWriter(resourcesPath);

            String line;
            while ((line = br.readLine()) != null) {
                String cleanedLine = line.replaceAll("\"", "");
                fw.write(cleanedLine + "\n");
            }

            System.out.println("Cleaned CSV file saved to: " + resourcesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
