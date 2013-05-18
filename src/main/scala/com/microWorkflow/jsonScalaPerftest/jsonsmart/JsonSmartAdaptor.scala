package com.microWorkflow.jsonScalaPerftest.jsonsmart

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import net.minidev.json.JSONValue.parse

class JsonSmartAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
      parse(json /* , classOf[Tweet] */)  // TODO: add map (JsonSmart v.2)
  }

  def mapTweet(json: String) = null

  override def hasMap = false

}
