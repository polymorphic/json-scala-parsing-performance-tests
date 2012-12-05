package com.microWorkflow.jsonScalaPerftest

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 12/2/12
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class LibraryAdapter extends TimeMeasurements {

  def initialize()

  def runOnce(json: String): Any

  def measure(json: String, iterations: Int) = {
    val startUserTime = getUserTime
    initialize()
    val initUserTime = getUserTime
    try {
      for (iteration <- 1 to iterations)
        runOnce(json)
      val endUserTime = getUserTime
      Measurement(iterations).setInitializationTime(startUserTime, initUserTime).setIterationTime(startUserTime, endUserTime)
    } catch {
      case e: Exception =>
        val measurement = Measurement(iterations)
        measurement.hasErrors = true
        measurement
    }
  }
}
