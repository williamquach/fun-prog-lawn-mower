package funprog.models.lawn_mower.grid

import play.api.libs.json.{JsObject, Json, Writes}

class GridLimits(val x: Int, val y: Int) {
  override def toString: String = {
    s"\t(x => ${x.toString}, y => ${y.toString})"
  }
}

object GridLimitsWrites {
  implicit val gridLimitsWrites: Writes[GridLimits] = new Writes[GridLimits] {
    def writes(gridLimits: GridLimits): JsObject = Json.obj(
        "x" -> gridLimits.x,
        "y" -> gridLimits.y
    )
  }

  def toCSV(gridLimits: GridLimits): String = {
    val xCsv = gridLimits.x.toString
    val yCsv = gridLimits.y.toString

    s"$xCsv,$yCsv"
  }
}
