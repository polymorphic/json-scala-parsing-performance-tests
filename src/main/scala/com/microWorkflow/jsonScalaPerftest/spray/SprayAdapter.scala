package com.microWorkflow.jsonScalaPerftest.spray

import com.microWorkflow.jsonScalaPerftest.LibraryAdapter
import spray.json._

class SprayAdapter extends LibraryAdapter {

  override def initialize() { /* nop */ }

  override def runOnce(json: String) = {
    json.asJson
  }

}
