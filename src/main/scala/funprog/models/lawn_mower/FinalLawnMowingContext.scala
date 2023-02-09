package funprog.models.lawn_mower

import funprog.models.lawn_mower.grid.{GridLimits, GridLimitsWrites}
import play.api.libs.json.{JsObject, Json, Writes}

class FinalLawnMowingContext(
    val gridLimits: GridLimits,
    val lawnMowers: List[FinalLawnMower]
) {
  override def toString: String = {
    s"Grid limits :\n${gridLimits.toString}\n" +
    s"Lawn mowers :\n${lawnMowers.map(lawnMower => lawnMower.toString).mkString("\n")}"
  }
}

object FinalLawnMowingContextWrites {
  implicit val finalLawnMowingContextWrites: Writes[FinalLawnMowingContext] =
    new Writes[FinalLawnMowingContext] {
      def writes(finalLawnMowingContext: FinalLawnMowingContext): JsObject =
        Json.obj(
            "limite" -> Json.toJson(finalLawnMowingContext.gridLimits)(
                GridLimitsWrites.gridLimitsWrites
            ),
            "tondeuses" -> Json.toJson(finalLawnMowingContext.lawnMowers)(
                FinalLawnMowerWrites.finalLawnMowerListWrites
            )
        )
    }

  def toCSV(finalLawnMowingContext: FinalLawnMowingContext): String = {
    val lawnMowersCsv = finalLawnMowingContext.lawnMowers
    .zipWithIndex
      .map {
        case (finalLawnMower, index) =>
          FinalLawnMowerWrites.toCSV(finalLawnMower, (index + 1).toString)
      }
      .mkString("")

    s"$lawnMowersCsv"
  }
}
