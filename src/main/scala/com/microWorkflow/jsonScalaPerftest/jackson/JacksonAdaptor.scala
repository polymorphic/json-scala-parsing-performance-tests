package com.microWorkflow.jsonScalaPerftest.jackson

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.microWorkflow.jsonScalaPerftest.domain.{Place, Tweet}

import com.fasterxml.jackson.module.scala.DefaultScalaModule

class JacksonAdaptor(name: String) extends LibraryAdaptor(name) {
  
  var m: ObjectMapper = _
  
  override def initialize() { 
    m = new ObjectMapper
    m.registerModule(new DefaultScalaModule)
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  override def parseOnce(json: String) = {
      m.readTree(json)
  }

  override def mapTweet(json: String) = {
      m.readValue(json, classOf[Tweet])
  }

  override def mapPlace(json: String) = {
    m.readValue(json, classOf[Place])
  }

  override def hasMap = true
}
