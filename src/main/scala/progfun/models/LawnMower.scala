package progfun.models

import progfun.models.Instruction.Instruction

class LawnMower(val gridPositionalInformation: GridPositionalInformation, val instructions: List[Instruction]) {
    override def toString: String = {
        s"\tGrid positional information :\n${gridPositionalInformation.toString}\n" +
            s"\t\tInstructions :\n\t\t${instructions.map(instruction => instruction.toString).mkString(", ")}" +
            s"\n\t====================="
    }
}
