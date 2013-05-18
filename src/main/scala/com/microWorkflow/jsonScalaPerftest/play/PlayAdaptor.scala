package com.microWorkflow.jsonScalaPerftest.play

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import play.api.libs.json._
import com.microWorkflow.jsonScalaPerftest.domain._

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

  def mapTweet(json: String) = {

    val parsed = Json.parse(json)
    parsed.as[Tweet]
  }
}
