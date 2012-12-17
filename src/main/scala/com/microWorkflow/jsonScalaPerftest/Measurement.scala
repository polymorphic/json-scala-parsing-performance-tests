/*
 * Copyright (c) 2012 Dragos Manolescu.
 *
 * Published under the Apache 2.0 license; see http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.microWorkflow.jsonScalaPerftest

import fr.janalyse.jmx.{RichMBean, JMX}

case class Measurement(name: String, value: Double, u: String="") {

  val unit = if (u.isEmpty) None else Some(u)

  override def toString = {
    val sb = new StringBuilder
    sb.append(name)
    sb.append(" = ")
    sb.append(value)
    sb.append(" ")
    sb.append(unit match {
      case Some(s) => s
      case None    => "(adimensional)"
    })
    sb.toString()
  }
}

object Measurement {

  def takeMeasurement(name: String) = {
    Measurement(name, getValuesFor(name, "Mean"), "ms")
  }

  def getDoubleFrom(bean: RichMBean, key: String) = {
    bean.getDouble(key) match {
      case Some(d) => d
      case None => 0.0
    }
  }

  def getValuesFor(nameKey: String, valueKey: String): Double = {
    JMX.once() {
      jmx => {
        jmx.mbeans()
          .filter(b => b.name.contains(nameKey.toCharArray))
          .map(bean => getDoubleFrom(bean, valueKey))
          .head
      }
    }
  }
}

