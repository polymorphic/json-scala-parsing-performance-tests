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

  def measure(adapter: LibraryAdapter, doMap: Boolean) {
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

  val adapters = Array(new liftjson.LiftJsonAdapter("lift")
    , new jerkson.JerksonAdapter("jerkson")
    , new jsonsmart.JsonSmartAdapter("JsonSmart")
    , new spray.SprayAdapter("spray")
    , new persist.PersistAdapter("persist")
    , new twitter.TwitterAdapter("twitter")
    , new socrata.SocrataAdapter("socrata")
    , new scalalib.ScalaLibAdapter("scalalib")
    , new jackson.JacksonAdapter("jackson")
  )

  val loopCounter = Metrics.newCounter(this.getClass, "main loop")

  val categories = Category.getFilesMatching("data", f => f.isDirectory).map(d => new Category(d.getCanonicalPath))

  def run(iterations: Int, doMap: Boolean) = {
    loopCounter.clear()
    val oldFiles = Category.getFilesMatching(measurementsPath, f => f.isFile)
    if (oldFiles != null)
      oldFiles.foreach(f => f.delete())
    CsvReporter.enable(new File(measurementsPath), 100, TimeUnit.MILLISECONDS)
    for (count <- 1 to iterations) {
      categories.flatMap(c => (adapters map {
        each => c.measure(each, doMap)
      }))
      loopCounter.inc()
    }
  }

  def takeMeasurements(iterations: Int) {
    val where = new File(measurementsPath)
    where.mkdirs()
    print("Priming caches...")
    run(iterations, true)
    print("Measuring timings w/o mapping...")
    run(iterations, false)
    Category.getFilesMatching(measurementsPath, f => f.isFile && f.getName.contains(".csv".toCharArray))
            .foreach(f => f.renameTo(new File("nomap" + f.getName)))
    print("Measuring timings w/ mapping...")
    run(iterations, true)
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


object Main extends App {
  val e = Experiment("/tmp/measurements1")
  e.takeMeasurements(10)
  println("Done")
}