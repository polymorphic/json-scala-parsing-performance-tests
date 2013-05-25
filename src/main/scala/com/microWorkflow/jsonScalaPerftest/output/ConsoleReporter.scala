package com.microWorkflow.jsonScalaPerftest.output

import scala.collection.immutable.HashMap
import com.microWorkflow.jsonScalaPerftest.Measurement

class ConsoleReporter(ms: Array[(String, HashMap[String, Measurement])]) {

  def printResults() {
    for ((category, measurements) <- ms) {
      println("Category: %s".format(category))
      for ((dataset, measurement) <- measurements)
      print("\t dataset '%s', measurement: %s\n".format(dataset, measurement))
    }

  }
}
