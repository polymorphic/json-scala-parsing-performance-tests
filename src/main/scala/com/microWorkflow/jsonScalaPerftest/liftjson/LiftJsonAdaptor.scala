package com.microWorkflow.jsonScalaPerftest.liftjson

import net.liftweb.json._
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

case class Url(indices: Array[Int], url: String)
case class Hashtag(indices: Array[Int], text: String)
case class UserMention(indices: Array[Int], name: String)
case class Entities(hashtags: Array[Hashtag], urls: Array[Url], userMentions: Array[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)

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

  override def mapOnce(json: String) = {
    implicit val formats = DefaultFormats
    parse(json) match {
      case obj: JObject => List(obj.extract[Tweet])
      case array: JArray => array.extract[List[Tweet]]
      case _ => List[Tweet]()
    }
  }

  override def hasMap = true
}
