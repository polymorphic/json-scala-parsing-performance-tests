package com.microWorkflow.jsonScalaPerftest.scalalib

import com.persist.JsonOps._
import com.persist.JsonMapper._
import com.microWorkflow.jsonScalaPerftest.{LibraryAdapter, TimeMeasurements}
import scala.util.parsing.json.JSON

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 11/24/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */

/*
case class Url(indices: Seq[Int], url: String)
case class Hashtag(indices: Seq[Int], text: String)
case class UserMention(indices: Seq[Int], name: String)
case class Entities(hashtags: Seq[Hashtag], urls: Seq[Url], user_mentions: Seq[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)
*/

class ScalaLibAdapter(name: String) extends LibraryAdapter(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    val root = JSON.parseFull(json)
    root
    /*
    Json(json) match {
      case obj: JsonObject => if (doMap) ToObject[Tweet](obj)
      case array: JsonArray => //array.extract[List[Tweet]]
      case _ => List[Tweet]()
    }
    */
  }

}
