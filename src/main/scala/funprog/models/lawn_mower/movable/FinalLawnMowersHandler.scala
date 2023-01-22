package funprog.models.lawn_mower.movable

import funprog.models.lawn_mower.movable.Instruction.Instruction
import funprog.models.lawn_mower.position.GridPositionalInformation
import funprog.models.lawn_mower.{LawnMower, LawnMowingContext}

class FinalLawnMowersHandler {
    def moveLawnMower(instructions: List[Instruction], lawnMower: MovableLawnMower): GridPositionalInformation = instructions match {
        case hd :: tl =>
            val newLawnMower = lawnMower.move(hd.toString)
            moveLawnMower(tl, newLawnMower)
        case _ => lawnMower.lawnMowerToGridPositionalInformation(lawnMower)
    }

    // TODO : Make this method return a LawnMowingContext with new lawn mowers
    def moveLawnMowers(lawnMowers: List[LawnMower], lawnMowingContext: LawnMowingContext): List[GridPositionalInformation] = lawnMowers match {
        case hd :: tl =>
            val newLawnMower = moveLawnMower(hd.instructions, new MovableLawnMower(lawnMowingContext.gridLimits, hd.gridPositionalInformation))
            newLawnMower :: moveLawnMowers(tl, lawnMowingContext)
        case _ => List()
    }

    def gridPositionalInformationListToString(lawnMowers: List[GridPositionalInformation]): String = lawnMowers match {
        case hd :: tl =>
            hd.position.x.toString + " " + hd.position.y.toString + " " + hd.direction.toString + "\n" + gridPositionalInformationListToString(tl)
        case _ => ""
    }
}