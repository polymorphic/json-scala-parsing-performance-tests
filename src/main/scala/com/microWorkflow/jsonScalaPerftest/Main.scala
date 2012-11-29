package com.microWorkflow.jsonScalaPerftest

import java.io.File
import io.Source
import collection.JavaConversions._

object Main extends App {

  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
     new File(dir).listFiles.filter(file => p(file))
  }

  def deserializeFrom(fileName: String) = {
    val contents = Source.fromFile(fileName).mkString
    val adapter = new liftjson.Adapter
    adapter.measure(contents, iterations = 1000)
  }

  val dataFolders = getFilesMatching("data", f => f.isDirectory)
  for (d <- dataFolders) {
    println("Testing files in " + d.getAbsolutePath)
    val files = getFilesMatching(d.getAbsolutePath, f => f.isFile)
    val measurements = files.map(f => deserializeFrom(f.getCanonicalPath))
    for (measurement <- measurements) {
      println(measurement)
    }
  }
}
