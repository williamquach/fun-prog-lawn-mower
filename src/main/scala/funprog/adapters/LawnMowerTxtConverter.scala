package funprog.adapters

import funprog.exceptions.DonneesIncorectesException
import funprog.models.{CardinalDirection, GridLimits, GridPositionalInformation, Instruction, LawnMower, LawnMowingContext, Position}
@SuppressWarnings(Array("org.wartremover.warts.Throw"))
class LawnMowerTxtConverter {
    def convert(parsedFile: String): LawnMowingContext = {
        if (parsedFile.isEmpty) {
            throw DonneesIncorectesException("Le fichier est vide.")
        }else {
            val lines = parsedFile.split("\n").toList
            val head = lines.headOption.getOrElse(throw DonneesIncorectesException("Erreur innatendue."))
            val gridLimits = getGridLimits(head)
            val lawnMowers = getLawnMowers(lines.slice(1, lines.length), gridLimits)
            new LawnMowingContext(gridLimits = gridLimits, lawnMowers = lawnMowers)
        }
    }

    private def getGridLimits(gridLimitsInTextFile: String): GridLimits = {
        gridLimitsInTextFile.split(" ").toList match {
            case width :: height :: Nil =>
                try {
                    new GridLimits(width.toInt, height.toInt)
                } catch {
                    case _: NumberFormatException => throw DonneesIncorectesException(s"La limite x: $width ou le y: $height n'est pas un entier.")
                }
            case _ => throw DonneesIncorectesException("Les limites de la pelouse ne sont pas renseignées correctement.")
        }
    }

    private def getLawnMowers(lawnMowersInTextFile: List[String], gridLimits: GridLimits): List[LawnMower] = {
        if (lawnMowersInTextFile.isEmpty)
            throw DonneesIncorectesException("Le fichier ne contient aucune tondeuse.")
        else if (lawnMowersInTextFile.length % 2 != 0)
            throw DonneesIncorectesException("Les informations ou les instructions d'au moins une tondeuse ne sont pas renseignées.")
        else {
            lawnMowersInTextFile.grouped(2).map {
                case mowerPosition :: mowerInstructions :: Nil =>
                    val mowerPositionList = mowerPosition.split(" ").toList match {
                        case x :: y :: direction :: Nil =>
                            try {
                                if (x.toInt > gridLimits.x)
                                    throw DonneesIncorectesException(s"Le x: $x de la tondeuse est hors de la pelouse.")
                                else if (y.toInt > gridLimits.y)
                                    throw DonneesIncorectesException(s"Le y: $y de la tondeuse est hors de la pelouse.")
                                else if (!CardinalDirection.values.map(_.toString).contains(direction))
                                    throw DonneesIncorectesException(s"La direction $direction de la tondeuse n'est pas reconnue.")
                                else List(x, y, direction)
                            } catch {
                                case _: NumberFormatException => throw DonneesIncorectesException(s"Le x: $x ou le y: $y n'est pas un entier.")
                            }
                        case _ => throw DonneesIncorectesException("Les informations de la tondeuse ne sont pas totalement renseignées.")
                    }
                    val mowerInstructionsList = mowerInstructions.split("").toList match {
                        case instructions if instructions.forall(Instruction.values.map(_.toString).contains) => instructions
                        case _ => throw DonneesIncorectesException("Des instructions ne sont pas reconnues.")
                    }
                    val mowerXYPose = mowerPositionList.headOption.getOrElse(throw DonneesIncorectesException("Erreur innatendue."))
                    new LawnMower(
                        new GridPositionalInformation(
                            new Position(mowerXYPose.toInt, mowerPositionList(1).toInt),
                            CardinalDirection.withName(mowerPositionList(2))
                        ),
                        mowerInstructionsList.map(instruction => Instruction.withName(instruction))
                    )
                case _ => throw DonneesIncorectesException("Erreur innatendue.")
            }.toList
        }
    }
}
