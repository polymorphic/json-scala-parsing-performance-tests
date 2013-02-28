package com.microWorkflow.jsonScalaPerftest.play

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import play.api.libs.json._

case class Url(indices: Array[Int], url: String)

case class HashTag(indices: Array[Int], text:String)

case class UserMention(indices: Array[Int], name: String)

case class Entities(hashtags: Array[HashTag], urls:Array[Url], user_mentions:Array[UserMention])

case class Tweet(id_str: String, text: String, entities: Entities)


class PlayAdaptor(name: String) extends LibraryAdaptor(name) {
  implicit val urlReads = Json.reads[Url]
  implicit val hashTagReads = Json.reads[HashTag]
  implicit val userMentionReads = Json.reads[UserMention]
  implicit val entitiesReads = Json.reads[Entities]
  implicit val tweetReads = Json.reads[Tweet]

  def hasMap = true

  def initialize() {}

  def parseOnce(json: String) = {
    Json.parse(json)
  }

  def mapOnce(json: String) = {

    val parsed = Json.parse(json)
    parsed.as[Tweet]
  }
}
