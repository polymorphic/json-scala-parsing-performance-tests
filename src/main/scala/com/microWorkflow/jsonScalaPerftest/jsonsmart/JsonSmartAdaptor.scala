package com.microWorkflow.jsonScalaPerftest.jsonsmart

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import net.minidev.json.JSONValue.parse

case class User ( utc_offset: Int
                  , time_zone: String
                  )

case class Url(indices: Array[Int], url: String)

case class Hashtag(indices: Array[Int], text: String)

case class UserMention(indices: Array[Int], name: String)

case class Entities(hashtags: Array[Hashtag], urls: Array[Url], userMentions: Array[UserMention])

case class Tweet(idStr: String, text: String, entities: Entities)



class JsonSmartAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    if (doMap)
      parse(json /* , classOf[Tweet] */)
    else
      parse(json)
  }

}
