package com.sheltonfrancisco.utils;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;

import java.io.InputStream;

public class DatasetLoader {

    public static Instances loadDataset(String filepath) throws Exception {
        InputStream inputStream = DatasetLoader.class.getClassLoader().getResourceAsStream(filepath);

        if (inputStream == null)
            throw new IllegalArgumentException("File not found! " + filepath);

        Instances dataset = null;

        if (filepath.endsWith(".csv")) {
            CSVLoader loader = new CSVLoader();
            loader.setSource(inputStream);
            dataset = loader.getDataSet();
        } else if (filepath.endsWith(".arff")) {
            dataset = new ConverterUtils.DataSource(inputStream).getDataSet();
        } else {
            throw new IllegalArgumentException("File format not supported! " + filepath);
        }

        dataset.setClassIndex(dataset.numAttributes() - 1);
        return dataset;
    }
}
