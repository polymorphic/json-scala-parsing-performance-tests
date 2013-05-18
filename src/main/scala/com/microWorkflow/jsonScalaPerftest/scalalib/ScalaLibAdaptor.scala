package com.microWorkflow.jsonScalaPerftest.scalalib

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import scala.util.parsing.json.JSON

class ScalaLibAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
      JSON.parseFull(json)
  }

  override def mapTweet(json: String) = null

  override def hasMap = false

}
