package funprog.adapters

import funprog.exceptions.DonneesIncorectesException
import funprog.models.{CardinalDirection, GridLimits, GridPositionalInformation, Instruction, LawnMower, LawnMowingContext, Position}
@SuppressWarnings(Array("org.wartremover.warts.Throw"))
class LawnMowerTxtConverter {
    def convert(parsedFile: String): LawnMowingContext = {
        val lines = parsedFile.split("\n").toList
        val head = lines.headOption.getOrElse(throw DonneesIncorectesException("Le fichier est vide."))
        val gridLimits = getGridLimits(head)
        val lawnMowers = getLawnMowers(lines.slice(1, lines.length))
        new LawnMowingContext(gridLimits = gridLimits, lawnMowers = lawnMowers)
    }

    private def getGridLimits(gridLimitsInTextFile: String): GridLimits = {
        gridLimitsInTextFile.split(" ").toList match {
            case width :: height :: Nil => new GridLimits(width.toInt, height.toInt)
            case _ => throw DonneesIncorectesException("Les limites de la pelouse ne sont pas renseignées correctement.")
        }
    }

    private def getLawnMowers(lawnMowersInTextFile: List[String]): List[LawnMower] = {
        lawnMowersInTextFile.grouped(2).map {
            case mowerPosition :: mowerInstructions :: Nil =>
                val mowerPositionList = mowerPosition.split(" ").toList
                val mowerInstructionsList = mowerInstructions.split("").toList
                val mowerXYPose = mowerPositionList.headOption.getOrElse(throw DonneesIncorectesException("La position de la tondeuse n'est pas renseignée correctement."))
                new LawnMower(
                    new GridPositionalInformation(
                        new Position(mowerXYPose.toInt, mowerPositionList(1).toInt),
                        CardinalDirection.withName(mowerPositionList(2))
                    ),
                    mowerInstructionsList.map(instruction => Instruction.withName(instruction))
                )
            case _ => throw DonneesIncorectesException("Les tondeuses ne sont pas correctes")
        }.toList
    }
}
