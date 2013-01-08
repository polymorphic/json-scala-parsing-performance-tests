/*
 * Copyright (c) 2013 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */

package com.microWorkflow.jsonScalaPerftest

import collection.immutable.HashMap

case class Experiment(warmUpIterations: Int=5) {

  val allAdaptors = Array( new liftjson.LiftJsonAdaptor("lift")
    , new jerkson.JerksonAdaptor("jerkson")
    , new jsonsmart.JsonSmartAdaptor("JsonSmart")
    , new spray.SprayAdaptor("spray")
    , new persist.PersistAdaptor("persist")
    , new twitter.TwitterAdaptor("twitter")
    , new rojoma.RojomaAdaptor("rojoma")
    , new scalalib.ScalaLibAdaptor("scalalib")
    , new jackson.JacksonAdaptor("jackson")
    )

  val categories = Category
    .getFilesMatching("data", f => f.isDirectory)
    .map(d => new Category(d.getCanonicalPath))

  def measureParsing(iterations: Int): Array[(String, HashMap[String, Measurement])] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Array[(String, HashMap[String, Measurement])] = {

      categories.flatMap(category => (libraryAdaptors.map {
        adaptor => (category.name -> category.measure(adaptor, false, iterations))
      }))
    }
    println("Parsing warm up (%d iterations)...".format(warmUpIterations))
    run(warmUpIterations, allAdaptors.toSeq)
    println("Parsing measurement (%d iterations)...".format(iterations))
    run(iterations, allAdaptors.toSeq)
  }

  def measureMapping(iterations: Int): Array[(String, HashMap[String, Measurement])] = {
    def run(iterations: Int, libraryAdaptors: Seq[LibraryAdaptor]): Array[(String, HashMap[String, Measurement])] = {

      categories.flatMap(category => (libraryAdaptors.map {
        adaptor => (category.name -> category.measure(adaptor, true, iterations))
      }))
    }

    val targetAdaptors = allAdaptors.filter(_.hasMap)
    println("Mapping warm up (%d iterations)...".format(warmUpIterations))
    run(warmUpIterations, targetAdaptors)
    println("Mapping measurement (%d iterations)...".format(iterations))
    run(iterations, targetAdaptors)
  }

}
