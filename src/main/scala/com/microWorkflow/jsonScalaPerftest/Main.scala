/*
 * Copyright (c) 2012 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.microWorkflow.jsonScalaPerftest

import joptsimple._
import scala.collection.JavaConversions._
import scala.collection.immutable.HashSet
import com.microWorkflow.jsonScalaPerftest.output.{ConsoleReporter, ChartReporter}


object Main extends App {
    val argParser = new OptionParser()
    val listOpt = argParser.accepts("list", "List known JSON libraries.")
    val iterationsOpt = argParser
      .accepts("iterations", "Number of iterations for measurements.")
      .withRequiredArg()
      .describedAs("iterations")
      .ofType(classOf[Int])
      .defaultsTo(200)
    val warmUpOpt = argParser
      .accepts("warmup", "Number of iterations for warm up.")
      .withRequiredArg()
      .describedAs("warmup")
      .ofType(classOf[Int])
      .defaultsTo(25)
    val mapOpt = argParser.accepts("map", "Cover parsing and object mapping.")
    val excludeOpt = argParser
      .accepts("exclude", "Comma-separated list of library names to exclude from run, e.g., twitter,scalalib.")
      .withRequiredArg()
      .withValuesSeparatedBy(',')
      .describedAs("exclude")
      .ofType(classOf[String])
    val reportOpt = argParser
      .accepts("report", "Result generation, c=console, b=bar chart.")
      .withRequiredArg()
      .describedAs("Character; default is 'c'")
      .ofType(classOf[String])
      .defaultsTo("c")

    val options = try {
      argParser.parse(args: _*)
    } catch { case e: OptionException => {
      println("Exception parsing command-line arguments: %s".format(e.getMessage))
      sys.exit(1)
    }}
    if (options.has(listOpt)) {
      println("Known JSON libraries:")
      Experiment.allAdaptors.foreach(a => println("%s, object mapping %s implemented".format(
        a.getName,
        if (a.hasMap) "IS" else "IS NOT")))
      sys.exit()
    }

    val iterations = options.valueOf[Int](iterationsOpt)
    val doMap = options.has(mapOpt)
    val warmUpIterations = options.valueOf[Int](warmUpOpt)
    val exclude = if (options.has(excludeOpt)) options.valuesOf[String](excludeOpt).map(_.toLowerCase).toSet else HashSet[String]()

    println("Running %d iterations %s object mapping".format(iterations, if (doMap) "with" else "without"))
    val experiment = Experiment(exclude, warmUpIterations)
    val ms = if (doMap) experiment.measureMapping(iterations) else experiment.measureParsing(iterations)

    if (options.has(reportOpt) && options.valueOf[String](reportOpt) == "b") {
      val chart = new ChartReporter(if (doMap) "JSON Parsing and Mapping" else "JSON Parsing", ms)
      chart.view
      System.in.read()
    } else {
      val reporter = new ConsoleReporter(ms)
      reporter.printResults()
    }
    sys.exit()
}