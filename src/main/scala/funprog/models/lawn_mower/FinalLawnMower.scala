package funprog.models.lawn_mower

import funprog.models.lawn_mower.movable.Instruction.Instruction
import funprog.models.lawn_mower.position.GridPositionalInformation

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
