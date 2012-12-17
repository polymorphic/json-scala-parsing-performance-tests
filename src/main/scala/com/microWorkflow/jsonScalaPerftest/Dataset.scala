/*
 * Copyright (c) 2012 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.microWorkflow.jsonScalaPerftest

import io.Source

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
