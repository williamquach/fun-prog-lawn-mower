package funprog.models.lawn_mower

import funprog.models.lawn_mower.movable.Instruction.Instruction
import funprog.models.lawn_mower.position.GridPositionalInformation

class LawnMower(val gridPositionalInformation: GridPositionalInformation, val instructions: List[Instruction]) {
    override def toString: String = {
        s"\tGrid positional information :\n${gridPositionalInformation.toString}\n" +
            s"\t\tInstructions :\n\t\t${instructions.map(instruction => instruction.toString).mkString(", ")}" +
            s"\n\t====================="
    }
}
