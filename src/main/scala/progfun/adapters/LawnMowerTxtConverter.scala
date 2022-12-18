package progfun.adapters

import progfun.models.{CardinalDirection, GridLimits, GridPositionalInformation, Instruction, LawnMower, LawnMowingContext, Position}

class LawnMowerTxtConverter {

    def convert(parsedFile: String): LawnMowingContext = {
        val lines = parsedFile.split("\r").toList
        if (lines.length < 3) {
            new LawnMowingContext(gridLimits = new GridLimits(0, 0), lawnMowers = List())
        }
        else {
            val gridLimits = getGridLimits(lines.head)
            val lawnMowers = getLawnMowers(lines.slice(1, lines.length))
            new LawnMowingContext(gridLimits = gridLimits, lawnMowers = lawnMowers)
        }
    }

    private def getGridLimits(gridLimitsInTextFile: String): GridLimits = {
        gridLimitsInTextFile.split(" ").toList match {
            case width :: height :: Nil => new GridLimits(width.toInt, height.toInt)
        }
    }

    private def getLawnMowers(lawnMowersInTextFile: List[String]): List[LawnMower] = {
        lawnMowersInTextFile.grouped(2).map {
            case mowerPosition :: mowerInstructions :: Nil =>
                val mowerPositionList = mowerPosition.split(" ").toList
                val mowerInstructionsList = mowerInstructions.split("").toList
                new LawnMower(
                    new GridPositionalInformation(
                        new Position(mowerPositionList.head.toInt, mowerPositionList(1).toInt),
                        CardinalDirection.withName(mowerPositionList(2))
                    ),
                    mowerInstructionsList.map(instruction => Instruction.withName(instruction))
                )
        }.toList
    }
}
