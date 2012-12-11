package com.microWorkflow.jsonScalaPerftest.liftjson

import net.liftweb.json._
import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 11/24/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */

case class Url(indices: Array[Int], url: String)
case class Hashtag(indices: Array[Int], text: String)
case class UserMention(indices: Array[Int], name: String)
case class Entities(hashtags: Array[Hashtag], urls: Array[Url], userMentions: Array[UserMention])
case class Tweet(id_str: String, text: String, entities: Entities)

class LiftJsonAdaptor(name: String) extends LibraryAdaptor(name) {

  override def initialize() { /* nop */ }

  override def runOnce(json: String, doMap:Boolean) = {
    implicit val formats = DefaultFormats
    parse(json) match {
      case obj: JObject => {
        if (doMap) {
           List(obj.extract[Tweet])   
        } else {
           obj
        }
      }
      case array: JArray => {
        if (doMap) {
          array.extract[List[Tweet]]
        } else {
          array
        }
      }
      case _ => List[Tweet]()
    }
  }

  override def hasMap = true
}
