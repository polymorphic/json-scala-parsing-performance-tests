package com.microWorkflow.jsonScalaPerftest.scalalib

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import scala.util.parsing.json.JSON

/*
case class Url(indices: Seq[Int], url: String)
case class Hashtag(indices: Seq[Int], text: String)
case class UserMention(indices: Seq[Int], name: String)
case class Entities(hashtags: Seq[Hashtag], urls: Seq[Url], user_mentions: Seq[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)
*/

class ScalaLibAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
      JSON.parseFull(json)
  }

  override def mapOnce(json: String) = null

  override def hasMap = false

}
