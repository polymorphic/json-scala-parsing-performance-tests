package com.microWorkflow.jsonScalaPerftest.persist

import com.persist.JsonOps._
import com.persist.JsonMapper._

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

case class Url(indices: Seq[Int], url: String)
case class Hashtag(indices: Seq[Int], text: String)
case class UserMention(indices: Seq[Int], name: String)
case class Entities(hashtags: Seq[Hashtag], urls: Seq[Url], user_mentions: Seq[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)

class PersistAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
    Json(json)
  }

  override def mapOnce(json: String) = {
    ToObject[Tweet](Json(json))
  }

  override def hasMap = true

}
