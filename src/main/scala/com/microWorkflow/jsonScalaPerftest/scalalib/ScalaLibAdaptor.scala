package com.microWorkflow.jsonScalaPerftest.scalalib

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
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

class ScalaLibAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    if (doMap)
      JSON.parseFull(json) // TODO: add map
    else
      JSON.parseFull(json)
  }

  override def hasMap = false

}
