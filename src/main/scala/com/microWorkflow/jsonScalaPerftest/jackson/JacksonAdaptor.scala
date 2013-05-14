package com.microWorkflow.jsonScalaPerftest.jackson

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.scala.DefaultScalaModule

case class Url(indices: Array[Int], url: String)

case class HashTag(indices: Array[Int], text:String)

case class UserMention(indices: Array[Int], name: String)

case class Entities(hashtags: Array[HashTag], urls:Array[Url], user_mentions:Array[UserMention])

case class Tweet(id_str: String, text: String, entities: Entities)

class JacksonAdaptor(name: String) extends LibraryAdaptor(name) {
  
  var m: ObjectMapper = _
  
  override def initialize() { 
    m = new ObjectMapper
//    m.registerModule(new DefaultScalaModule)
//    m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  override def parseOnce(json: String) = {
      m.readTree(json)
  }

  override def mapOnce(json: String) = {
      m.readValue(json, classOf[Tweet])
  }

  override def hasMap = true
}
