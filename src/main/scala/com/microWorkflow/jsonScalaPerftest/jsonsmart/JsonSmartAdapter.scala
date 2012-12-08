package com.microWorkflow.jsonScalaPerftest.jsonsmart

import com.microWorkflow.jsonScalaPerftest.{LibraryAdapter, TimeMeasurements}
import net.minidev.json.JSONValue.parse

class JsonSmartAdapter(name: String) extends LibraryAdapter(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    parse(json)
  }

}
