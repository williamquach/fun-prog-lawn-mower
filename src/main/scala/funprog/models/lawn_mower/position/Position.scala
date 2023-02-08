package funprog.models.lawn_mower.position

import play.api.libs.json.{JsObject, Json, Writes}

class Position(val x: Int, val y: Int) {
  override def toString: String = {
    s"(x => ${x.toString}, y => ${y.toString})"
  }
}

object PositionWrites {
  implicit val positionWrites: Writes[Position] = new Writes[Position] {
    def writes(position: Position): JsObject = Json.obj(
        "x" -> position.x,
        "y" -> position.y
    )
  }

  def toCSV(position: Position): String = {
    val xCsv = position.x.toString
    val yCsv = position.y.toString

    s"$xCsv;$yCsv"
  }
}
