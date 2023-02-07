package funprog.models.lawn_mower.movable

import funprog.models.lawn_mower.movable.Instruction.Instruction
import funprog.models.lawn_mower.position.GridPositionalInformation
import funprog.models.lawn_mower.{
  FinalLawnMower,
  FinalLawnMowingContext,
  LawnMower,
  LawnMowingContext
}

class FinalLawnMowersHandler {
  def createFinalLawnMower(
      start: GridPositionalInformation,
      instructions: List[Instruction],
      end: GridPositionalInformation
  ): FinalLawnMower = {
    new FinalLawnMower(start, instructions, end)
  }

  def moveLawnMower(
      instructions: List[Instruction],
      lawnMower: MovableLawnMower
  ): GridPositionalInformation = instructions match {
    case hd :: tl =>
      val newLawnMower = lawnMower.move(hd.toString)
      moveLawnMower(tl, newLawnMower)
    case _ => lawnMower.lawnMowerToGridPositionalInformation(lawnMower)
  }

  def moveLawnMowers(
      lawnMowers: List[LawnMower],
      lawnMowingContext: LawnMowingContext
  ): List[FinalLawnMower] = lawnMowers match {
    case hd :: tl =>
      val newLawnMower = moveLawnMower(
          hd.instructions,
          new MovableLawnMower(
              lawnMowingContext.gridLimits,
              hd.gridPositionalInformation
          )
      )
      createFinalLawnMower(
          hd.gridPositionalInformation,
          hd.instructions,
          newLawnMower
      ) :: moveLawnMowers(tl, lawnMowingContext)
    case _ => List()
  }

  def createFinalLawnMowersContext(
      lawnMowers: List[LawnMower],
      lawnMowingContext: LawnMowingContext
  ): FinalLawnMowingContext = {
    val finalLawnMowers = moveLawnMowers(lawnMowers, lawnMowingContext)
    new FinalLawnMowingContext(lawnMowingContext.gridLimits, finalLawnMowers)
  }
}
