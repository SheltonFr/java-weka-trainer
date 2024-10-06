package com.sheltonfrancisco;

import com.sheltonfrancisco.utils.DatasetLoader;
import com.sheltonfrancisco.utils.ModelEvaluator;
import com.sheltonfrancisco.utils.ModelTrainer;
import com.sheltonfrancisco.utils.ModelUtils;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;

public class Main {
    public static void main(String[] args) throws Exception {
        Instances dataset = DatasetLoader.loadDataset("datasets/Housing.csv", "price");
        System.out.println(dataset.toSummaryString());

        Remove remove = new Remove();
        remove.setInputFormat(dataset);
        remove.setInvertSelection(false);
        remove.setAttributeIndices("5,6,8,12,last");

        Instances preparedDataSet = Remove.useFilter(dataset, remove);

        for (int i = 0; i < preparedDataSet.numInstances(); i++) {
            Instance instance = preparedDataSet.instance(i);

            if (!instance.classIsMissing()) {
                double price = instance.classValue();
                instance.setClassValue(price / 1000);
            }
        }


        MultilayerPerceptron multilayerPerceptron = ModelTrainer.trainModel(MultilayerPerceptron.class, preparedDataSet);
        LinearRegression linearRegression = ModelTrainer.trainModel(LinearRegression.class, preparedDataSet);
        SMOreg smOreg = ModelTrainer.trainModel(SMOreg.class, preparedDataSet);
        RandomForest randomForest = ModelTrainer.trainModel(RandomForest.class, preparedDataSet);

        ModelEvaluator.compareClassifiers(preparedDataSet, multilayerPerceptron, linearRegression, smOreg, randomForest);
        ModelEvaluator.predictions(preparedDataSet.firstInstance(), multilayerPerceptron, linearRegression, smOreg, randomForest);

        System.out.println("==========WRITING MODELS TO DISK==========");
        ModelUtils.saveModel(multilayerPerceptron, "housing/multilayer_perceptron.model");
        ModelUtils.saveModel(linearRegression, "housing/linear_regression.model");
        ModelUtils.saveModel(smOreg, "housing/svm.model");
        ModelUtils.saveModel(randomForest, "housing/random_forest.model");

    }
}