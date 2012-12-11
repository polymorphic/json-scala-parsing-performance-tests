package com.microWorkflow.jsonScalaPerftest.jsonsmart

import com.microWorkflow.jsonScalaPerftest.{LibraryAdapter, TimeMeasurements}
import net.minidev.json.JSONValue.parse
import com.microWorkflow.jsonScalaPerftest.spray.Tweet

class JsonSmartAdapter(name: String) extends LibraryAdapter(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    if (doMap)
      parse(json, classOf[Tweet])
    else
      parse(json)
  }

}
