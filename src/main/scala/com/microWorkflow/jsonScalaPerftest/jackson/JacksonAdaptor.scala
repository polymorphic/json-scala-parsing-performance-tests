package com.microWorkflow.jsonScalaPerftest.jackson

import com.microWorkflow.jsonScalaPerftest.LibraryAdaptor
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.map.DeserializationConfig

class Url {
  private var indices:Array[Int] = _
  private var url:String = _
  def getIndices = indices
  def setIndices(v:Array[Int]) { indices = v}
  def getUrl = url
  def setUrl(s:String) { url = s}
}
class HashTag {
  private var indices:Array[Int] = _
  private var text:String = _
  def getIndices = indices
  def setIndices(v:Array[Int]) { indices = v}
  def getText = text
  def setText(s:String) { text = s}
}
class UserMention {
  private var indices:Array[Int] = _
  private var name:String = _
  def getIndices = indices
  def setIndices(v:Array[Int]) { indices = v}
  def getName = name
  def setName(s:String) { name = s}
}
class Entities {
  private var hashtags:Array[HashTag] = _
  private var urls:Array[Url] = _
  private var user_mentions:Array[UserMention] = _
  def getHashtags = hashtags
  def setHashtags(v:Array[HashTag]) { hashtags = v }
  def getUrls = urls
  def setUrls(v:Array[Url]) { urls = v }
  def getUser_mentions = user_mentions
  def setUser_mentions(v:Array[UserMention]) { user_mentions = v }
}

class Tweet {
  private var id_str:String = _
  private var text:String = _
  private var entities:Entities = _
  def getId_str = id_str
  def setId_str(s:String) { id_str = s}
  def getText = text
  def setText(s:String) { text = s}
  def getEntities = entities
  def setEntities(e:Entities) { entities = e}
}

class JacksonAdaptor(name: String) extends LibraryAdaptor(name) {
  
  var m: ObjectMapper = _
  
  override def initialize() { 
    m = new ObjectMapper()
    m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  override def parseOnce(json: String) = {
      m.readTree(json)
  }

  override def mapOnce(json: String) = {
      m.readValue(json, classOf[Tweet])
  }

  override def hasMap = true
}
