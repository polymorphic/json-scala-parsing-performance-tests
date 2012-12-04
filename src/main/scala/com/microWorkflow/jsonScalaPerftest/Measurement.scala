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
  var uTime: Long = _

  def setLabel(s: String) = {
    label = s
    this
  }

  def setUserTime(t0: Long, t1: Long) = {
    uTime = t1 - t0
    this
  }

  def measurementLabel = label
  def userTime = uTime

  override def toString = {
    val sb = new StringBuilder
    sb.append("====\n")
    sb.append(measurementLabel)
    sb.append("\nIterations:\t")
    sb.append(iterations)
    sb.append("\nTotal user time:\t")
    sb.append(userTime)
    sb.append(" ns\nUser time/operation:\t")
    sb.append("%f".format(userTime * 1.0 / iterations))
    sb.append(" ns")
    sb.toString()
  }
}
