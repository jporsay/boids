package ar.edu.itba.ss.command

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage

import org.jzy3d.chart.AWTChart
import org.jzy3d.colors.Color
import org.jzy3d.colors.ColorMapper
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.javafx.JavaFXChartFactory
import org.jzy3d.javafx.JavaFXRenderer3d
import org.jzy3d.javafx.controllers.mouse.JavaFXCameraMouseController
import org.jzy3d.maths.Range
import org.jzy3d.plot3d.builder.Builder
import org.jzy3d.plot3d.builder.Mapper
import org.jzy3d.plot3d.primitives.Scatter
import org.jzy3d.plot3d.primitives.Shape
import org.jzy3d.plot3d.rendering.canvas.Quality
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.ScatterMultiColor
import java.util.*


/**
 * Showing how to pipe an offscreen Jzy3d chart image to a JavaFX ImageView.
 *
 * [JavaFXChartFactory] delivers dedicated  [JavaFXCameraMouseController]
 * and [JavaFXRenderer3d]
 *
 * Support
 * Rotation control with left mouse button hold+drag
 * Scaling scene using mouse wheel
 * Animation (camera rotation with thread)
 *
 * TODO :
 * Mouse right click shift
 * Keyboard support (rotate/shift, etc)
 *
 * @author Martin Pernollet
 */
class DemoJzy3dFX : Application() {

    override fun start(stage: Stage) {
        stage.title = DemoJzy3dFX::class.java.simpleName

        // Jzy3d
        val factory = JavaFXChartFactory()
        val chart = getDemoChart(factory, "offscreen")
        val imageView = factory.bindImageView(chart)

        // JavaFX
        val pane = BorderPane()
        val scene = Scene(pane)
        stage.scene = scene
        stage.show()
        pane.center = imageView

        factory.addSceneSizeChangedListener(chart, scene)

        stage.width = 500.0
        stage.height = 500.0
    }

    private fun getDemoChart(factory: JavaFXChartFactory, toolkit: String): AWTChart {

        // -------------------------------
        // Create a chart
        val quality = Quality.Advanced
        quality.isSmoothPolygon = true
        quality.isAnimated = true

        // let factory bind mouse and keyboard controllers to JavaFX node
        val chart = factory.newChart(quality, toolkit) as AWTChart
        val scatter = createScatter()
        chart.scene.graph.add(scatter)


        return chart
    }

    private fun createScatter(): ScatterMultiColor {
        val scatter = ScatterMultiColor(createData(), ColorMapper(ColorMapRainbow(), -0.5, 0.5))
        scatter.setWidth(4f)
        return scatter
    }

    private fun createData(): Array<Coord3d?> {
        val size = 500
        var x: Float
        var y: Float
        var z: Float

        val points = arrayOfNulls<Coord3d>(size)

        val r = Random()
        r.setSeed(0)

        for (i in 0 until size) {
            x = r.nextFloat() - 0.5f
            y = r.nextFloat() - 0.5f
            z = r.nextFloat() - 0.5f
            points[i] = Coord3d(x, y, z)
        }
        return points
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(DemoJzy3dFX::class.java)
        }
    }
}
