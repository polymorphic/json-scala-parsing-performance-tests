package com.microWorkflow.jsonScalaPerftest

import management.ManagementFactory

/**
 * Created with IntelliJ IDEA.
 * User: dam
 * Date: 11/25/12
 * Time: 7:38 AM
 * To change this template use File | Settings | File Templates.
 */
trait TimeMeasurements {

  def getCpuTime: Long = {
    val bean = ManagementFactory.getThreadMXBean( )
    if (bean.isCurrentThreadCpuTimeSupported) bean.getCurrentThreadCpuTime else 0
  }

  def getUserTime: Long = {
    val bean = ManagementFactory.getThreadMXBean
    if (bean.isCurrentThreadCpuTimeSupported) bean.getCurrentThreadUserTime else 0
  }

  def getSystemTime: Long = {
    val bean = ManagementFactory.getThreadMXBean
    if (bean.isCurrentThreadCpuTimeSupported) (bean.getCurrentThreadCpuTime - bean.getCurrentThreadUserTime) else 0L
  }

}
