package com.microWorkflow.jsonScalaPerftest

import java.io.File
import io.Source
import collection.JavaConversions._
import collection.immutable.HashMap

object Main extends App {

  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
     new File(dir).listFiles.filter(file => p(file))
  }

  def deserializeFrom(fileName: String, adapter: LibraryAdapter) = {
    val contents = Source.fromFile(fileName).mkString
    adapter.measure(contents, iterations = 1000).setLabel(fileName)
  }

  val adapters = Array( new liftjson.LiftJsonAdapter
                      , new jerkson.JerksonAdapter
                      , new jsonsmart.JsonSmartAdapter
                      , new spray.SprayAdapter
                      )

  val dataFolders = getFilesMatching("data", f => f.isDirectory)
  for (d <- dataFolders) {
    println("Testing files in " + d.getAbsolutePath)
    val files = getFilesMatching(d.getAbsolutePath, f => f.isFile)
    val measurements = adapters.foldLeft(new HashMap[String, Array[Measurement]]){ (m,e) =>
      m + (e.getClass.toString -> files.map(f => deserializeFrom(f.getCanonicalPath, e)))
    }


    for (adapterName <- measurements.keys) {
      println("\n******** %s ********".format(adapterName))
      for (measurement <- measurements(adapterName))
        println(measurement)
    }
  }
}
