package com.microWorkflow.jsonScalaPerftest

/**
 * Created with IntelliJ IDEA.
 * User: SERVICE-NOW\dragos.manolescu
 * Date: 12/4/12
 * Time: 10:45 PM
 */
class PrettyPrinter {
  val sb = new StringBuilder

  def print(m: Measurement) {
    sb.append("====\n")
    sb.append(m.measurementLabel)
    sb.append("\nIterations:\t")
    sb.append(m.iterations)
    if (m.hasErrors) {
      sb.append("\nInvalid")
    } else {
      sb.append("\nTotal time (user):\t")
      sb.append(m.iterationTime)
      sb.append("ns \nOne-time initialization (user):\t")
      sb.append(m.initializationTime)
      sb.append(" ns\nTime/operation (user):\t")
      sb.append("%f".format(m.iterationTime * 1.0 / m.iterations))
      sb.append(" ns")
    }
    sb.append('\n')
  }

  override def toString = sb.mkString
}
