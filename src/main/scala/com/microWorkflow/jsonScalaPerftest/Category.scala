/*
 * Copyright (c) 2012 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.microWorkflow.jsonScalaPerftest

import java.io.File
import collection.immutable.HashMap

case class Category(directoryName: String, name: String) {

  def this(fn: String) = {
    this(fn, fn.substring(fn.lastIndexOf('/') + 1))
  }

  val datasets = Category.getFilesMatching(directoryName, fn => fn.isFile).map {
    each => new Dataset(each.getCanonicalPath)
  }

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(datasets.map(ds => ds.toString).mkString(": ", ", ", ""))
    sb.toString()
  }

  def measure(adaptor: LibraryAdaptor, doMap: Boolean, iterations: Int, warmUp: Int): HashMap[String, Measurement] = {
    datasets.foldLeft(new HashMap[String, Measurement])((m,d) => {
      print("Warming up... ")
      adaptor.measure(d, doMap, warmUp)
      adaptor.resetTimers()
      print(" Measuring...")
      adaptor.measure(d, doMap, iterations)
      println(" Completed")
      m + (d.name -> Measurement.takeMeasurement(adaptor.timerName))
    })
  }
}

object Category {
  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
    new File(dir).listFiles.filter(file => p(file))
  }
}
