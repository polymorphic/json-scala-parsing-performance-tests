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

  def parseOnce(json: String): Any

  def mapTweet(json: String): Any

  def mapPlace(json: String) = { /* nop */ }

  def measure(dataset: Dataset, doMap:Boolean, iterations: Int) {
    initialize()

    val context2 = mainTimer.time()

    val mapper: String => Any = dataset.name match {
      case "100tweets" => mapTweet
      case _ => mapPlace
    }

    val f: String => Any = if (doMap) mapper else parseOnce

    try {
      for (count <- 1 to iterations) {
        for (doc <- dataset.docs)
          f(doc)
      }
    } catch {
      case ex: Exception => println("%s bombed while processing %s (%s)".format(name, dataset.name, ex.getMessage))
    } finally {
      context2.stop()
    }
  }

  def resetTimers() {
    mainTimer.clear()
  }
}
