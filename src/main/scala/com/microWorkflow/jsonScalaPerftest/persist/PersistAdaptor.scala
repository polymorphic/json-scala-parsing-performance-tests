package com.microWorkflow.jsonScalaPerftest.persist

import com.persist.JsonOps._
import com.persist.JsonMapper._

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import com.microWorkflow.jsonScalaPerftest.domain.{Place, Tweet}

class PersistAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
    Json(json)
  }

  override def mapTweet(json: String) = {
    ToObject[Tweet](Json(json))
  }

  override def mapPlace(json: String) = {
    ToObject[Place](Json(json))
  }

  override def hasMap = true

}
