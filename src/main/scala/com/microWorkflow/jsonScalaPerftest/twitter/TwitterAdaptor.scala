package com.microWorkflow.jsonScalaPerftest.twitter

import com.twitter.json.Json.parse
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

class TwitterAdaptor(name:String) extends LibraryAdaptor(name) {

  def initialize() {}
  
  def runOnce(json: String, doMap:Boolean) = {
    if (doMap)
      parse(json)
    else
      parse(json)
  }

  override def hasMap = false

}
