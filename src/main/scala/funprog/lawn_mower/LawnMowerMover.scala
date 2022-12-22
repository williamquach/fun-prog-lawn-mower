package funprog.lawn_mower

import funprog.models.Instruction.Instruction
import funprog.models.{GridPositionalInformation, LawnMowingContext}

class LawnMowerMover {
  def moveLawnMower(instructions: List[Instruction], lawnMower: LawnMower): GridPositionalInformation = instructions match {
    case hd :: tl =>
      val newLawnMower = lawnMower.move(hd.toString)
      moveLawnMower(tl, newLawnMower)
    case _ => lawnMower.lawnMowerToGridPositionalInformation(lawnMower)
  }

  def moveLawnMowers(lawnMowers: List[funprog.models.LawnMower], lawnMowingContext: LawnMowingContext): List[GridPositionalInformation] = lawnMowers match {
    case hd :: tl =>
      val newLawnMower = moveLawnMower(hd.instructions, new LawnMower(lawnMowingContext.gridLimits, hd.gridPositionalInformation))
      newLawnMower :: moveLawnMowers(tl, lawnMowingContext)
    case _ => List()
  }

  def gridPositionalInformationListToString(lawnMowers: List[GridPositionalInformation]): String = lawnMowers match {
    case hd :: tl =>
      hd.position.x.toString + " " + hd.position.y.toString + " " + hd.direction.toString + "\n" + gridPositionalInformationListToString(tl)
    case _ => ""
  }
}