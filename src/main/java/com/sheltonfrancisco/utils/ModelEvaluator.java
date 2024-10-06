package com.sheltonfrancisco.utils;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Random;

public class ModelEvaluator {


    @SafeVarargs
    public static <T extends AbstractClassifier> void predictions(Instance instance, T... classifiers) throws Exception {
        System.out.println("=========================INSTANCE PREDICTION=========================");
        System.out.println("Actual price: " + instance.classValue());

        for (T classifier : classifiers) {
            double prediction = classifier.classifyInstance(instance);
            System.out.printf("{ classifier: %s, prediction: %.2f }\n", classifier.getClass().getSimpleName(), prediction);
        }

        System.out.println("=========================END=========================");
    }

    @SafeVarargs
    public static <T extends AbstractClassifier> void compareClassifiers(Instances data, T... classifiers) throws Exception {

        for (T classifier : classifiers) {
            Evaluation evaluation = new Evaluation(data);
            evaluation.crossValidateModel(classifier, data, 10, new Random(1));

            System.out.println("Classifier: " + classifier.getClass().getSimpleName());
            System.out.println("Mean Absolute Error: " + evaluation.meanAbsoluteError());
            System.out.println("Root Mean Squared Error: " + evaluation.rootMeanSquaredError());
            System.out.println("Relative Absolute Error: " + evaluation.relativeAbsoluteError() + " %");
            System.out.println("Root Relative Squared Error: " + evaluation.rootRelativeSquaredError() + " %");
            System.out.println("----------------------------------------");
        }

    }

    public static <T extends AbstractClassifier> void evaluateModel(T classifier, Instances dataset) throws Exception {
        Evaluation evaluation = new Evaluation(dataset);
        evaluation.evaluateModel(classifier, dataset);

        System.out.println(evaluation.toSummaryString("\nResults\n======\n", false));

        if (classifier instanceof MultilayerPerceptron) {
            MultilayerPerceptron mlp = (MultilayerPerceptron) classifier;
            System.out.println("Learning rate: " + mlp.getLearningRate());
            System.out.println("Momentum: " + mlp.getMomentum());
            System.out.println("Training time: " + mlp.getTrainingTime());
        }

    }
}
