package com.microWorkflow.jsonScalaPerftest.output

import scala.collection.immutable.HashMap
import com.microWorkflow.jsonScalaPerftest.Measurement
import org.jfree.data.category.{CategoryDataset, DefaultCategoryDataset}
import org.jfree.ui.{RefineryUtilities, ApplicationFrame}
import org.jfree.chart.{ChartFactory, JFreeChart, ChartPanel}
import scala.Predef._
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.axis.{CategoryLabelPositions, NumberAxis}
import org.jfree.chart.renderer.category.BarRenderer
import java.awt.image.BufferedImage
import java.io.FileOutputStream
import java.awt.geom.Rectangle2D
import com.sun.image.codec.jpeg.JPEGCodec


class ChartReporter(title: String, results: Array[(String, HashMap[String, Measurement])]) extends ApplicationFrame(title) {

  import java.awt.Color
  import java.awt.Dimension

  val graphDataSet = createDataset()

  val chart = createChart(graphDataSet)
  val chartPanel = new ChartPanel(chart)
  chartPanel.setPreferredSize(new Dimension(500, 270))
  setContentPane(chartPanel)

  def createDataset(): DefaultCategoryDataset = {
    val categoryDataSet = new DefaultCategoryDataset()

    for ((category, datasets) <- results) {
      for ((dataset, measurement) <- datasets) {
        categoryDataSet.addValue(measurement.value, measurement.name, dataset)
      }
    }
    categoryDataSet
  }

  def createChart(dataset: CategoryDataset): JFreeChart = {

    // create the chart...
    val chart = ChartFactory.createBarChart(
      title, // chart title
      "JSON Data set", // domain axis label
      "Time (ms)", // range axis label
      dataset, // data
      PlotOrientation.VERTICAL, // orientation
      true, // include legend
      true, // tooltips?
      false // URLs?
    )

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

    // set the background color for the chart...
    chart.setBackgroundPaint(Color.white)

    // get a reference to the plot for further customisation...
    val plot = chart.getCategoryPlot()
    plot.setBackgroundPaint(Color.lightGray)
    plot.setDomainGridlinePaint(Color.white)
    plot.setRangeGridlinePaint(Color.white)

    // set the range axis to display integers only...
    val rangeAxis = plot.getRangeAxis().asInstanceOf[NumberAxis]
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits())

    // disable bar outlines...
    val renderer = plot.getRenderer().asInstanceOf[BarRenderer]
    renderer.setDrawBarOutline(false)
    renderer.setShadowVisible(false)

    val domainAxis = plot.getDomainAxis()
    domainAxis.setCategoryLabelPositions(
      CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
    )
    chart
  }


  def view {
    this.pack()
    RefineryUtilities.centerFrameOnScreen(this)
    this.setVisible(true)
  }

  def draw(chart: JFreeChart, width: Int, height: Int): BufferedImage = {
    val img =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g2 = img.createGraphics()

    chart.draw(g2, new Rectangle2D.Double(0, 0, width, height))

    g2.dispose()
    img
  }

  def saveToFile(
                  aFileName: String,
                  width: Int,
                  height: Int,
                  quality: Double) {
    val img = draw(chart, width, height)

    val fos = new FileOutputStream(aFileName)
    val encoder2 =
      JPEGCodec.createJPEGEncoder(fos)
    val param2 = encoder2.getDefaultJPEGEncodeParam(img)
    param2.setQuality(quality.toFloat, true)
    encoder2.encode(img, param2)
    fos.close()
  }

}
