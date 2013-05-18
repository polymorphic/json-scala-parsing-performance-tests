package com.microWorkflow.jsonScalaPerftest.domain

case class Url(indices: Array[Int], url: String)

case class HashTag(indices: Array[Int], text:String)

case class UserMention(indices: Array[Int], name: String)

case class Entities(hashtags: Array[HashTag], urls:Array[Url], user_mentions:Array[UserMention])

case class Tweet(id_str: String, text: String, entities: Entities)
