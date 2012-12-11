package com.microWorkflow.jsonScalaPerftest.socrata

import spray.json._
import com.rojoma.json.io.JsonReader
import com.microWorkflow.jsonScalaPerftest.{LibraryAdaptor, TimeMeasurements}


class SocrataAdaptor(name:String) extends LibraryAdapter(name) {

  def initialize() {}
  
  def runOnce(json: String, doMap:Boolean) = {

    val tweets = JsonReader.fromString(json)
    tweets
  }

}
