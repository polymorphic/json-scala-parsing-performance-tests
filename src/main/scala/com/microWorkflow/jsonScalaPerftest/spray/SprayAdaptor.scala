package com.microWorkflow.jsonScalaPerftest.spray

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import spray.json._
import com.microWorkflow.jsonScalaPerftest.domain._

object myJsonProtocol extends DefaultJsonProtocol {
    implicit val urlFormat = jsonFormat2(Url)
    implicit val hashtagFormat = jsonFormat2(HashTag)
    implicit val userMentionFormat = jsonFormat2(UserMention)
    implicit val entitiesFormat = jsonFormat3(Entities)
    implicit val tweetFormat = jsonFormat3(Tweet)
}

class SprayAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def parseOnce(json: String) = {
      json.asJson
  }

  override def mapTweet(json: String) = {
      import myJsonProtocol._
      json.asJson.convertTo[Tweet]
  }

  override def hasMap = true

}
