package funprog.models.lawn_mower.position

import funprog.models.lawn_mower.position.CardinalDirection.Direction
import play.api.libs.json.{JsObject, Json, Writes}

class GridPositionalInformation(
    val position: Position,
    val direction: Direction
) {
  override def toString: String = {
    s"\t\tPosition => ${position.toString}\n" +
    s"\t\tDirection => ${direction.toString}"
  }
}

object GridPositionalInformationWrites {
  implicit val gridPositionalInformationWrites
      : Writes[GridPositionalInformation] =
    new Writes[GridPositionalInformation] {
      def writes(
          gridPositionalInformation: GridPositionalInformation
      ): JsObject =
        Json.obj(
            "point" -> Json.toJson(gridPositionalInformation.position)(
                PositionWrites.positionWrites
            ),
            "direction" -> Json.toJson(
                gridPositionalInformation.direction.toString
            )
        )
    }

  def toCSV(gridPositionalInformation: GridPositionalInformation): String = {
    val positionCsv = PositionWrites.toCSV(gridPositionalInformation.position)
    val directionCsv = gridPositionalInformation.direction.toString

    s"$positionCsv;$directionCsv"
  }
}
