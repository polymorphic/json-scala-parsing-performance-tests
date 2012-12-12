package com.microWorkflow.jsonScalaPerftest

import java.io.File
import io.Source
import com.yammer.metrics.reporting.CsvReporter
import java.util.concurrent.TimeUnit
import fr.janalyse.jmx.JMX
import com.yammer.metrics.Metrics

case class Dataset(fileName: String, name: String) {

  def this(fn: String) = {
    this(fn, fn.substring(fn.lastIndexOf('/') + 1, fn.lastIndexOf('.')))
  }

  val docs: List[String] = Source.fromFile(fileName).getLines().toList

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(" (")
    sb.append(docs.length)
    sb.append(" documents)")
    sb.toString()
  }
}

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

  def measure(adapter: LibraryAdaptor, doMap: Boolean) {
    for (dataset <- datasets) {
      adapter.measure(dataset, doMap)
    }
  }
}

object Category {
  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
    new File(dir).listFiles.filter(file => p(file))
  }
}


case class Experiment(measurementsPath: String) {

  val adapters = Array(new liftjson.LiftJsonAdaptor("lift")
    , new jerkson.JerksonAdaptor("jerkson")
    , new jsonsmart.JsonSmartAdaptor("JsonSmart")
    , new spray.SprayAdaptor("spray")
    , new persist.PersistAdaptor("persist")
    , new twitter.TwitterAdaptor("twitter")
    , new rojoma.RojomaAdaptor("rojoma")
    , new scalalib.ScalaLibAdaptor("scalalib")
    , new jackson.JacksonAdaptor("jackson")
  )

  val loopCounter = Metrics.newCounter(this.getClass, "main loop")

  val categories = Category.getFilesMatching("data", f => f.isDirectory).map(d => new Category(d.getCanonicalPath))

  def run(iterations: Int, doMap: Boolean) = {
    loopCounter.clear()
    val oldFiles = Category.getFilesMatching(measurementsPath, f => f.isFile)
    println("Removing %d old files".format(oldFiles.length))
    if (oldFiles != null)
      oldFiles.foreach(f => f.delete())
    CsvReporter.enable(new File(measurementsPath), 100, TimeUnit.MILLISECONDS)
    val adaptersToTest = if (doMap) adapters.filter(_.hasMap) else adapters
    for (count <- 1 to iterations) {
      categories.flatMap(c => (adaptersToTest.map {
        each => c.measure(each, doMap)
      }))
      loopCounter.inc()
    }
  }

  def takeMeasurements(iterations: Int, doMap: Boolean) {
    val where = new File(measurementsPath)
    where.mkdirs()
    print("Priming caches...")
    run(iterations, doMap)
    print("Measuring timings...")
    run(iterations, doMap)
  }

  def printMeanValues() {
    JMX.once() {
      jmx => {
        val beans = jmx.mbeans().filter(b => b.name.contains("microWorkflow".toCharArray))
        for (bean <- beans) {
          val mean = bean.getDouble("Mean") match {
            case Some(d) => d
            case None => 0.0
          }
          println("%s: %fms".format(bean.name.substring(bean.name.indexOf('=') + 1), mean))
        }
      }
    }
  }
}


object Main {

  def main(args: Array[String]) {
    val where = if (args.length>0) args(0) else "/tmp/measurements"
    val iterations:Int = if (args.length>1) args(1).toInt else 100
    val doMap = if (args.length>2) args(2).equalsIgnoreCase("-map") else false
    println("Runing %d iterations %s object mapping; test results in %s"
      .format(iterations, if (doMap) "with" else "without", where))
    val e = Experiment(where)
    e.takeMeasurements(iterations, doMap)
    println("Done")
  }
}