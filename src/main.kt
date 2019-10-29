
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.stage.Stage
import kotlin.math.pow

fun f(x: Double): Number = x.pow(-2)
fun g(x: Double): Number = x.pow(-3)
fun h(x: Double): Number = x.pow(2)
fun i(x: Double): Number = x.pow(3)

val xBounds = Pair(-10,10)
val yBounds = Pair(-10,10)
const val stepSize = 0.1

class App : Application() {
    override fun start(primaryStage: Stage) {
        val functions = listOf(::f, ::g, ::h, ::i)

        val xAxis = NumberAxis(xBounds.first.toDouble(), xBounds.second.toDouble(), 1.0)
        val yAxis = NumberAxis(yBounds.first.toDouble(), yBounds.second.toDouble(), 1.0)
        val chart = LineChart<Number,Number>(xAxis, yAxis)
        chart.createSymbols = false

        for(function in functions) {
            val list = HashSet<XYChart.Data<Number, Number>>()
            var i: Double = xBounds.first.toDouble()
            while (i <= xBounds.second) {
                var data = Pair(i, function(i))

                list.add(XYChart.Data(data.first, data.second))
                i += stepSize
            }
            println("Computed " + list.size + " positions!")
            val series: XYChart.Series<Number, Number> = XYChart.Series()
            series.data.addAll(list)
            series.name = function.name
            chart.data.add(series)
        }

        val scene = Scene(chart, 1000.0, 500.0)
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(App::class.java)
        }
    }
}