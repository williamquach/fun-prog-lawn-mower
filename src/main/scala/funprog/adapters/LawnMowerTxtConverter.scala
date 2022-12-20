package funprog.adapters

import funprog.models.{CardinalDirection, GridLimits, GridPositionalInformation, Instruction, LawnMower, LawnMowingContext, Position}

class LawnMowerTxtConverter {

    def convert(parsedFile: String): LawnMowingContext = {
        val lines = parsedFile.split("\n").toList
        if (lines.length < 3) {
            new LawnMowingContext(gridLimits = new GridLimits(0, 0), lawnMowers = List())
        }
        else {
            val head = lines.headOption.getOrElse("-1 -1") // TODO How to handle this case?
            val gridLimits = getGridLimits(head)
            val lawnMowers = getLawnMowers(lines.slice(1, lines.length))
            new LawnMowingContext(gridLimits = gridLimits, lawnMowers = lawnMowers)
        }
    }

    private def getGridLimits(gridLimitsInTextFile: String): GridLimits = {
        gridLimitsInTextFile.split(" ").toList match {
            case width :: height :: Nil => new GridLimits(width.toInt, height.toInt)
            case _ => new GridLimits(-1, -1)
        }
    }

    private def getLawnMowers(lawnMowersInTextFile: List[String]): List[LawnMower] = {
        lawnMowersInTextFile.grouped(2).map {
            case mowerPosition :: mowerInstructions :: Nil =>
                val mowerPositionList = mowerPosition.split(" ").toList
                val mowerInstructionsList = mowerInstructions.split("").toList
                val mowerXYPose = mowerPositionList.headOption.getOrElse("-1")
                new LawnMower(
                    new GridPositionalInformation(
                        new Position(mowerXYPose.toInt, mowerPositionList(1).toInt),
                        CardinalDirection.withName(mowerPositionList(2))
                    ),
                    mowerInstructionsList.map(instruction => Instruction.withName(instruction))
                )
            case _ => new LawnMower(new GridPositionalInformation(new Position(-1, -1), CardinalDirection.N), List())
        }.toList
    }
}
