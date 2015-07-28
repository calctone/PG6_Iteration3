package edu.umkc.ic

import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.linalg.Vectors

/**
 * Created by Jeff Group6 on 7/19/15.
 */
object PG6_MLlib {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("MLlib")
    val sc = new SparkContext(conf)

    // Load 2 types of emails from text files: spam and ham (non-spam).
    // Each line has text from one email.
    val spam = sc.textFile("spam.txt")
    val ham = sc.textFile("ham.txt")

    // Create a HashingTF instance to map email text to vectors of 100 features.
    val tf = new HashingTF(numFeatures = 100)
    // Each email is split into words, each word is mapped to one feature
    val spamFeatures = spam.map(email => tf.transform(email.split(" ")))
    val hamFeatures = ham.map(email => tf.transform(email.split(" ")))

    // Create LabeledPoint datasets for positive (spam) and negative (ham) examples.
    val positiveExamples = spamFeatures.map(features => LabeledPoint(1, features))
    val negativeExamples = hamFeatures.map(features => LabeledPoint(0, features))
    val trainingData = positiveExamples.union(negativeExamples)
    trainingData.cache() // Cache data since Logistic Regression is an iterative algorithm

    // Create a Logistic Regression learner which uses the LBFGS optimizer.
    val lrLearner = new LogisticRegressionWithSGD()
    // Run the actual learning algorithm on the training data.
    val model = lrLearner.run(trainingData)

    // Test on a positive example (spam) and a negative one (ham).
    // First apply the same HashingTF feature transformation used on the training data.
    val posTestExample = tf.transform("O M G GET cheap stuff by sending money to ...".split (" "))
    val negTextExample = tf.transform("Hi, Dad, I started studying Spark the other ...".split(" "))

    // Now used the learned model to predict spam/ham for new emails.
    println(s"Prediction for positive test example: ${model.predict(posTestExample)}")
    println(s"Prediction for negative test example: ${model.predict(negTextExample)}")

    // Create the dense vector
    val denseVec1 = Vectors.dense(1.0, 2.0, 3.0)
    val denseVec2 = Vectors.dense(Array(1.0, 2.0, 3.0))

    // Create the sparse vector
    val sparseVec1 = Vectors.sparse(4, Array(0, 2), Array(1.0, 2.0))

    sc.stop()
  }
}
