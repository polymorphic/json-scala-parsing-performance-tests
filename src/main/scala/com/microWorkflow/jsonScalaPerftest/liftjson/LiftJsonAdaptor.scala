package com.microWorkflow.jsonScalaPerftest.liftjson

import net.liftweb.json._
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import com.microWorkflow.jsonScalaPerftest.domain.{Tweet}

class LiftJsonAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
    implicit val formats = DefaultFormats
    parse(json) match {
      case obj: JObject => obj
      case array: JArray => array
      case _ => JNull
    }
  }

  override def mapTweet(json: String) = {
    implicit val formats = DefaultFormats
    parse(json) match {
      case obj: JObject =>
        List(obj.extract[Tweet])
      case array: JArray =>
        array.extract[List[Tweet]]
      case _ => List[Tweet]()
    }
  }

  override def hasMap = true
}
