package funprog.models.lawn_mower

import funprog.models.lawn_mower.movable.Instruction._
import funprog.models.lawn_mower.position._
import play.api.libs.json.{JsObject, Json, Writes}

class FinalLawnMower(
    val start: GridPositionalInformation,
    val instructions: List[Instruction],
    val end: GridPositionalInformation
) {
  override def toString: String = {
    s"\tStart :\n${start.toString}\n" +
    s"\tInstructions :\n\t\t${instructions.map(instruction => instruction.toString).mkString(", ")}\n" +
    s"\tEnd :\n${end.toString}\n" +
    s"\n\t====================="
  }
}

object FinalLawnMowerWrites {
  implicit val finalLawnMowerWrites: Writes[FinalLawnMower] =
    new Writes[FinalLawnMower] {
      def writes(finalLawnMower: FinalLawnMower): JsObject = Json.obj(
          "start" -> Json.toJson(finalLawnMower.start)(
              GridPositionalInformationWrites.gridPositionalInformationWrites
          ),
          "instructions" -> Json.toJson(
              finalLawnMower.instructions
                .map(instruction => instruction.toString)
          ),
          "end" -> Json.toJson(finalLawnMower.end)(
              GridPositionalInformationWrites.gridPositionalInformationWrites
          )
      )
    }

  implicit val finalLawnMowerListWrites: Writes[List[FinalLawnMower]] =
    Writes.list[FinalLawnMower](finalLawnMowerWrites)

  def toCSV(finalLawnMower: FinalLawnMower, niemeRow: String): String = {
    val startCsv = GridPositionalInformationWrites.toCSV(finalLawnMower.start)
    val instructionsCsv = finalLawnMower.instructions
      .map(instruction => instruction.toString)
      .mkString("")
    val endCsv = GridPositionalInformationWrites.toCSV(finalLawnMower.end)

    s"$niemeRow;$startCsv;$endCsv;$instructionsCsv\n"
  }
}
