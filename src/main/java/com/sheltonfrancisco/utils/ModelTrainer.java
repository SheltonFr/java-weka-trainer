package com.sheltonfrancisco.utils;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;

public class ModelTrainer {

    public static <T extends AbstractClassifier> T trainModel(Class<T> classifierClass, Instances instances) throws Exception {
        T classifier = classifierClass.getDeclaredConstructor().newInstance();
        classifier.buildClassifier(instances);
        return classifier;
    }
}
