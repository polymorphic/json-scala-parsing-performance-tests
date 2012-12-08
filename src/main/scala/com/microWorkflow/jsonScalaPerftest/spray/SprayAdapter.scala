package com.microWorkflow.jsonScalaPerftest.spray

import com.microWorkflow.jsonScalaPerftest.LibraryAdapter
import spray.json._

case class Url(indices: Array[Int], url: String)
case class Hashtag(indices: Array[Int], text: String)
case class UserMention(indices: Array[Int], name: String)
case class Entities(hashtags: Array[Hashtag], urls: Array[Url], user_mentions: Array[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)
object myJsonProtocol extends DefaultJsonProtocol {
    implicit val urlFormat = jsonFormat2(Url)
    implicit val hashtagFormat = jsonFormat2(Hashtag)
    implicit val userMentionFormat = jsonFormat2(UserMention)
    implicit val entitiesFormat = jsonFormat3(Entities)
    implicit val tweetFormat = jsonFormat3(Tweet)
}

class SprayAdapter(name: String) extends LibraryAdapter(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    if (doMap) {
       import myJsonProtocol._
       json.asJson.convertTo[Tweet]
    } else {
      json.asJson
    }
  }

}
