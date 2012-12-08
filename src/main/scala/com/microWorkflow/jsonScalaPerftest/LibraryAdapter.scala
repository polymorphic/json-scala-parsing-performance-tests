package com.microWorkflow.jsonScalaPerftest

import com.yammer.metrics.Metrics
import java.util.concurrent.TimeUnit

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 12/2/12
 * Time: 7:58 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class LibraryAdapter(name: String) extends TimeMeasurements {
  lazy val initTimer = Metrics.newTimer(getClass, "%s-init".format(name), TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS)
  lazy val mainTimer = Metrics.newTimer(getClass, "%s-main".format(name), TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS)

  def getName = name

  def initialize()

  def runOnce(json: String, doMap:Boolean): Any

  def measure(dataset: Dataset,doMap:Boolean) = {
    val context1 = initTimer.time()
    try {
      initialize()
    } finally {
      context1.stop()
    }

    val context2 = mainTimer.time()
    try {
      for (doc <- dataset.docs)
        runOnce(doc, doMap)
    } catch {
      case ex:Exception => println("%s bombed while processing %s (%s)".format(name, dataset.name, ex.getMessage))
    } finally {
      context2.stop()
    }
  }
}
