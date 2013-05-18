package com.microWorkflow.jsonScalaPerftest.rojoma

import com.rojoma.json.io.JsonReader
import com.rojoma.json.util.{JsonUtil, SimpleJsonCodecBuilder}
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

case class User(utcOffset: Int, timeZone: String)
object User {
  implicit val jCodec = SimpleJsonCodecBuilder[User].build(
    "utc_offset", _.utcOffset,
    "time_zone", _.timeZone
  )
}

case class Url(indices: Seq[Int], url: String)
object Url {
  implicit val jCodec = SimpleJsonCodecBuilder[Url].build(
    "indices", _.indices,
    "url", _.url
  )
}

case class Hashtag(indices: Seq[Int], text: String)
object Hashtag {
  implicit val jCodec = SimpleJsonCodecBuilder[Hashtag].build(
    "indices", _.indices,
    "text", _.text
  )
}

case class UserMention(indices: Seq[Int], name: String)
object UserMention {
  implicit val jCodec = SimpleJsonCodecBuilder[UserMention].build(
    "indices", _.indices,
    "name", _.name
  )
}

case class Entities(hashtags: Seq[Hashtag], urls: Seq[Url], userMentions: Seq[UserMention])
object Entities {
  implicit val jCodec = SimpleJsonCodecBuilder[Entities].build(
    "hashtags", _.hashtags,
    "urls", _.urls,
    "user_mentions", _.userMentions
  )
}

case class Tweet(idStr: String, text: String, entities: Entities)
object Tweet {
  implicit val jCodec = SimpleJsonCodecBuilder[Tweet].build(
    "id_str", _.idStr,
    "text", _.text,
    "entities", _.entities
  )
}

class RojomaAdaptor(name:String) extends LibraryAdaptor(name) {

  def initialize() {}
  
  def mapTweet(json: String) = {
      JsonUtil.parseJson[Tweet](json).get
  }

  def parseOnce(json: String) = {
      JsonReader.fromString(json)
  }

  override def hasMap = true

}
