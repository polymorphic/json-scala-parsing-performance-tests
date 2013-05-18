package com.microWorkflow.jsonScalaPerftest.twitter

import com.twitter.json.Json.parse
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

class TwitterAdaptor(name:String) extends LibraryAdaptor(name) {

  def initialize() {}
  
  def parseOnce(json: String) = {
      parse(json)
  }

  override def mapTweet(json: String) = null

  override def hasMap = false

}
