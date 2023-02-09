package funprog.models

import funprog.models.lawn_mower.FinalLawnMowingContext
import funprog.models.lawn_mower.grid.GridLimits
import funprog.models.lawn_mower.movable.Instruction
import funprog.models.lawn_mower.position.CardinalDirection.Direction
import funprog.models.lawn_mower.position.{GridPositionalInformation, Position}

object YamlSerializer {

  def serializePosition(position: Position): String = {
    "\n    point:" + "\n        x: " + position.x.toString + "\n        y: " + position.y.toString
  }

  def serializeGridLimits(gridLimits: GridLimits): String = {
    "\n  x: " + gridLimits.x.toString + "\n  y: " + gridLimits.y.toString
  }

  def serializeDirection(direction: Direction): String = {
    direction.toString
  }

  def serializeInstructions(
      instructions: List[Instruction.Instruction]
  ): String = {
    instructions
      .map { instruction =>
        "\n  - " + instruction.toString
      }
      .mkString("")
  }

  def serializeGridPositionalInformation(
      gridPositionalInformation: GridPositionalInformation
  ): String = {
    serializePosition(gridPositionalInformation.position) + "\n    direction: " + serializeDirection(
        gridPositionalInformation.direction
    )
  }

  def serializeLawnMowers(
      lawnMowers: List[funprog.models.lawn_mower.FinalLawnMower]
  ): String = {
    lawnMowers
      .map { finalLawnMower =>
        "\n- debut:" + serializeGridPositionalInformation(finalLawnMower.start) +
        "\n  instructions:" + serializeInstructions(finalLawnMower.instructions) +
        "\n  fin:" + serializeGridPositionalInformation(finalLawnMower.end)
      }
      .mkString("")
  }

  def serializeFinalLawnMowingContext(
      finalLawnMowingContext: FinalLawnMowingContext
  ): String = {
    "limit:" + serializeGridLimits(finalLawnMowingContext.gridLimits) +
    "\ntondeuses:" + serializeLawnMowers(finalLawnMowingContext.lawnMowers)
  }
}
