package edu.umkc.ic

import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkConf, SparkContext}
/**
 * Created by Jeff Group 6 on 21-Jul-15.
 */
object PG6_CollaborativeFilteringALS {
  def main(args: Array[String]) {
    val sparkConf =new SparkConf().setMaster("local[*]").setAppName("Group6_CollaborativeFilteringALS").set("spark.driver.memory", "3g").set("spark.executor.memory", "3g")
    val sc = new SparkContext(sparkConf)

    // Load the location/crime/rating data
    val data = sc.textFile("crimeCF.csv")
    val ratings = data.map(_.split(',') match { case Array(locationId, crimeId, severityRating) =>
      Rating(locationId.toInt, crimeId.toInt, severityRating.toDouble)
    })

    // Build the recommendation model using ALS
    val rank = 10
    val numIterations = 20
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    // Evaluate the model on rating data
    val usersProducts = ratings.map { case Rating(locationId, crimeId, severityRating) =>
      (locationId, crimeId)
    }
    usersProducts.foreach(x=>println(x))
    val predictions =
      model.predict(usersProducts).map { case Rating(locationId, crimeId, severityRating) =>
        ((locationId, crimeId), severityRating)
      }
    predictions.foreach(x=>println(x))
    val ratesAndPreds = ratings.map { case Rating(locationId, crimeId, severityRating) =>
      ((locationId, crimeId), severityRating)
    }.join(predictions)
    val MSE = ratesAndPreds.map { case ((locationId, crimeId), (r1, r2)) =>
      val err = (r1 - r2)
      err * err
    }.mean()
    println("Mean Squared Error = " + MSE)
  }
}
