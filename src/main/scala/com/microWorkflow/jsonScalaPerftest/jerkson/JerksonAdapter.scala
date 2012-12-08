package com.microWorkflow.jsonScalaPerftest.jerkson

import com.codahale.jerkson.JsonSnakeCase
import org.codehaus.jackson.map.ObjectMapper
import com.codahale.jerkson.Json._
import com.microWorkflow.jsonScalaPerftest.{LibraryAdapter, TimeMeasurements}

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 11/24/12
 * Time: 7:00 AM
 * To change this template use File | Settings | File Templates.
 */

@JsonSnakeCase
case class User ( utc_offset: Int
                  , time_zone: String
                  )

@JsonSnakeCase
case class Url(indices: Array[Int], url: String)

@JsonSnakeCase
case class Hashtag(indices: Array[Int], text: String)

@JsonSnakeCase
case class UserMention(indices: Array[Int], name: String)

@JsonSnakeCase
case class Entities(hashtags: Array[Hashtag], urls: Array[Url], userMentions: Array[UserMention])

@JsonSnakeCase
case class Tweet(idStr: String, text: String, entities: Entities)


class JerksonAdapter(name: String) extends LibraryAdapter(name) {
 var mapper: ObjectMapper = _

  override def initialize() {
    mapper = new ObjectMapper()
  }

  override def runOnce(json: String, doMap:Boolean) = {
      try {
        parse[Tweet](json)
      } catch {
        case pe: com.codahale.jerkson.ParsingException =>
          null
        case iae: java.lang.IllegalArgumentException =>
          null
      }
  }

}
