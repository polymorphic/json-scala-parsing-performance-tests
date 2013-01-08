/*
 * Copyright (c) 2012 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.microWorkflow.jsonScalaPerftest

import collection.immutable.HashMap


object Main {
  val usage =
    """
      |Usage: com.microWorkflow.jsonScalaPerftest.Main [-n num] [-w num] [-map]
      |Running with default values
    """.stripMargin

  def main(args: Array[String]) {
    if (args.isEmpty) println(usage)

    type SymbolTable = Map[Symbol, Any]
    def nextOption(map: SymbolTable, list: List[String]): SymbolTable = {
      list match {
        case Nil => map
        case "-n" :: value :: tail => nextOption(map + ('iterations -> value.toInt), tail)
        case "-w" :: value :: tail => nextOption(map + ('warmUp -> value.toInt), tail)
        case "-map" :: tail => nextOption(map + ('map -> true), tail)
        case _ :: tail =>
          println("Don't know what to do with '%s'%s".format(list.head, usage))
          sys.exit(1)
      }
    }
    val options = nextOption(new HashMap[Symbol, Any](), args.toList)

    val iterations = options.getOrElse('iterations, 100).asInstanceOf[Int]
    val doMap = options.getOrElse('map, false).asInstanceOf[Boolean]
    val warmUpIterations = options.getOrElse('warmUp, 5).asInstanceOf[Int]

    println("Runing %d iterations %s object mapping".format(iterations, if (doMap) "with" else "without"))
    val experiment = Experiment(warmUpIterations)
    val ms = if (doMap) experiment.measureMapping(iterations) else experiment.measureParsing(iterations)
    for (m <- ms) {
      print("Category: %s\n".format(m._1))
      for (d <- m._2.iterator)
      print("\t dataset '%s', measurement: %s\n".format(d._1, d._2))
    }

    sys.exit()
  }
}