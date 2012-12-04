package com.microWorkflow.jsonScalaPerftest.jsonsmart

import com.microWorkflow.jsonScalaPerftest.{LibraryAdapter, TimeMeasurements}
import net.minidev.json.JSONValue.parse

class JsonSmartAdapter extends LibraryAdapter {

  override def initialize() { /* nop */ }

  override def runOnce(json: String) = {
    parse(json)
  }

}
