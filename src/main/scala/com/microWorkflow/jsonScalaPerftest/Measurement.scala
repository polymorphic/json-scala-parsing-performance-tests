package com.microWorkflow.jsonScalaPerftest

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 12/3/12
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
case class Measurement(iterations: Int) {
  var label: String = _
  var initializationUserTime: Long = _
  var iterationUserTime: Long = _

  var hasErrors = false

  def setLabel(s: String) = {
    label = s
    this
  }

  def setInitializationTime(startTime: Long, endTime: Long) = {
    require(endTime >= startTime)
    initializationUserTime = endTime - startTime
    this
  }

  def setIterationTime(startTime: Long, endTime: Long) = {
    require(endTime >= startTime)
    iterationUserTime = endTime - startTime
    this
  }

  def measurementLabel = label
  def initializationTime = initializationUserTime
  def iterationTime = iterationUserTime

}
