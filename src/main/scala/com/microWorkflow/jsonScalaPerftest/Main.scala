package com.microWorkflow.jsonScalaPerftest

import java.io.{FileFilter, File}
import io.Source

object Main extends App {

  def getFilesMatching(path: String, p: File => Boolean) = {
    val dir = new java.io.File(path).getAbsolutePath
     new File(dir).listFiles.filter(file => p(file))
  }

  def deserializeFrom(fileName: String) {
    val contents = Source.fromFile(fileName).mkString
    println("Read " + contents)
  }

  val dataFolders = getFilesMatching("data", f => f.isDirectory)
  for (d <- dataFolders) {
    println("Testing files in " + d.getAbsolutePath)
    val files = getFilesMatching(d.getAbsolutePath, f => f.isFile)
    for (f <- files)
      deserializeFrom(f.getAbsolutePath)
  }
}
