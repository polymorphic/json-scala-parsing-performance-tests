package com.microWorkflow.jsonScalaPerftest.twitter

//import com.microWorkflow.jsonScalaPerftest.TimeMeasurements
import com.twitter.json.Json.parse
import com.microWorkflow.jsonScalaPerftest.{LibraryAdaptor, TimeMeasurements}

class TwitterAdaptor(name:String) extends LibraryAdapter(name) {

  def initialize() {}
  
  def runOnce(json: String, doMap:Boolean) = {
    val tweets = parse(json)
    tweets
  }

}
