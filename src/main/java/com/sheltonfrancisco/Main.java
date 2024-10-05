package com.sheltonfrancisco;

import com.sheltonfrancisco.utils.DatasetLoader;
import com.sheltonfrancisco.utils.ModelUtils;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;

public class Main {
    public static void main(String[] args) throws Exception {
        Instances dataset = DatasetLoader.loadDataset("datasets/plano_saude.arff");


        // Build model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(dataset);
        System.out.println(model.getRevision());
        System.out.println("LR FORMULA: " + model);

    }
}