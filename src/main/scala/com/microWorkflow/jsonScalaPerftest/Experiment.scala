/*
 * Copyright (c) 2013 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.microWorkflow.jsonScalaPerftest

import collection.immutable.{Set, HashMap}

case class Experiment(exclude: Set[String], warmUpIterations: Int=5) {

  val adaptorsToTest = Experiment.allAdaptors.filterNot(s => exclude.contains(s.getName))

  val categories = Category
    .getFilesMatching("data", f => f.isDirectory)
    .map(d => new Category(d.getCanonicalPath))

  def measureParsing(iterations: Int): Array[(String, HashMap[String, Measurement])] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Array[(String, HashMap[String, Measurement])] = {

      categories.flatMap(category => (libraryAdaptors.map {
        adaptor => (category.name -> category.measure(adaptor, doMap = false, iterations, warmUpIterations))
      }))
    }

    println("Parsing measurement (%d warmup, %d iterations on %s)...".format(warmUpIterations, iterations, adaptorsToTest.map(_.getName).mkString(", ")))
    run(iterations, adaptorsToTest.toSeq)
  }

  def measureMapping(iterations: Int): Array[(String, HashMap[String, Measurement])] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Array[(String, HashMap[String, Measurement])] = {

      categories.flatMap(category => (libraryAdaptors.map {
        adaptor => (category.name -> category.measure(adaptor, doMap = true, iterations, warmUpIterations))
      }))
    }

    val targetAdaptors = adaptorsToTest.filter(_.hasMap)
    println("Mapping measurement (%d warmup, %d iterations on %s)...".format(warmUpIterations, iterations, adaptorsToTest.map(_.getName).mkString(", ")))
    run(iterations, targetAdaptors)
  }

}

object Experiment {

  val allAdaptors = Array( new liftjson.LiftJsonAdaptor("lift")
    , new jsonsmart.JsonSmartAdaptor("JsonSmart")
    , new spray.SprayAdaptor("spray")
    , new persist.PersistAdaptor("persist")
    , new rojoma.RojomaAdaptor("rojoma")
    , new twitter.TwitterAdaptor("twitter")
    , new scalalib.ScalaLibAdaptor("scalalib")
    , new jackson.JacksonAdaptor("jackson")
    , new play.PlayAdaptor("play")
  )
}
