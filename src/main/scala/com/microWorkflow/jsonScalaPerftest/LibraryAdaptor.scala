/*
 * Copyright (c) 2012 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.microWorkflow.jsonScalaPerftest

import com.yammer.metrics.Metrics
import java.util.concurrent.TimeUnit

abstract class LibraryAdaptor(name: String) extends TimeMeasurements {

  val timerName = "%s-main".format(name)

  lazy val mainTimer = Metrics.newTimer(getClass, timerName, TimeUnit.MILLISECONDS, TimeUnit.SECONDS)

  def getName = name

  def hasMap: Boolean

  def initialize()

  def runOnce(json: String, doMap:Boolean): Any

  def measure(dataset: Dataset, doMap:Boolean, iterations: Int) {
    initialize()

    val context2 = mainTimer.time()
    try {
      for (count <- 1 to iterations) {
        for (doc <- dataset.docs)
          runOnce(doc, doMap)
      }
    } catch {
      case ex:Exception => println("%s bombed while processing %s (%s)".format(name, dataset.name, ex.getMessage))
    } finally {
      context2.stop()
    }
  }

  def resetTimers() {
    mainTimer.clear()
  }
}
