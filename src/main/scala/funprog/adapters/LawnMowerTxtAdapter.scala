package funprog.adapters

import funprog.exceptions.DonneesIncorectesException
import funprog.models.lawn_mower.grid.GridLimits
import funprog.models.lawn_mower.movable.Instruction
import funprog.models.lawn_mower.position.{
  CardinalDirection,
  GridPositionalInformation,
  Position
}
import funprog.models.lawn_mower.{LawnMower, LawnMowingContext}

import scala.util.{Failure, Success, Try}

/**
 * Adapter converting a text file to a [[LawnMowingContext]].
 */
class LawnMowerTxtAdapter extends LawnMowerAdapter {
  def parseFileToDomain(parsedFile: String): Try[LawnMowingContext] = {
    if (parsedFile.isEmpty) {
      Failure(DonneesIncorectesException("Le fichier est vide."))
    } else {
      val lines = parsedFile.split("\n").toList
      val head = lines.headOption
      head match {
        case None =>
          Failure(
              DonneesIncorectesException("Le fichier ne contient pas de ligne.")
          )
        case Some(head) =>
          getGridLimits(head)
            .flatMap(gridLimits => {
              getLawnMowers(lines.slice(1, lines.length), gridLimits)
                .flatMap(
                    lawnMowers =>
                      Success(new LawnMowingContext(gridLimits, lawnMowers))
                )
            })
            .recoverWith({
              case exception: DonneesIncorectesException =>
                Failure(DonneesIncorectesException(exception.getMessage))
              case exception: Exception =>
                Failure(
                    DonneesIncorectesException(
                        "Erreur inattendue : " + exception.getMessage
                    )
                )
            })
      }
    }
  }

  private def getGridLimits(gridLimitsInTextFile: String): Try[GridLimits] = {
    gridLimitsInTextFile.split(" ").toList match {
      case width :: height :: Nil =>
        try {
          Success(new GridLimits(width.toInt, height.toInt))
        } catch {
          case _: NumberFormatException =>
            Failure(
                DonneesIncorectesException(
                    s"Une des limites de la grille x: $width ou y: $height n'est pas un entier."
                )
            )
        }
      case _ =>
        Failure(
            DonneesIncorectesException(
                s"Les limites de la grille ne sont pas au bon format. Elles doivent être de la forme x y. " +
                  s"Exemple : 5 5. Elles sont actuellement sous la forme : '$gridLimitsInTextFile'"
            )
        )
    }
  }

  private def getLawnMowers(
      lawnMowersInTextFile: List[String],
      gridLimits: GridLimits
  ): Try[List[LawnMower]] = {
    if (lawnMowersInTextFile.isEmpty)
      Failure(
          DonneesIncorectesException("Le fichier ne contient pas de tondeuse.")
      )
    else if (lawnMowersInTextFile.length % 2 != 0) {
      Failure(
          DonneesIncorectesException(
              "Les informations et/ou les instructions d'une tondeuse sont incorrectement formatées. " +
                "Elles doivent être de la forme x y orientation et une suite d'instructions. " +
                "Exemple : \n'1 2 N\nAADADAGGA'\nElles sont actuellement sous la forme : '" + lawnMowersInTextFile
                .mkString("\n") + "'"
          )
      )
    } else {
      val parsed = lawnMowersInTextFile
        .grouped(2)
        .map(lawnMowerInTextFile => {
          parseLawnMower(lawnMowerInTextFile, gridLimits)
        })
        .toList

      parsed.filter(_.isFailure) match {
        case Nil =>
          Try(parsed.collect({
            case Success(lawnMower) => lawnMower
          }))
        case failures =>
          Failure(
              DonneesIncorectesException(
                  "Une ou plusieurs tondeuse(s) sont incorrectement formatées : \n" +
                    failures
                      .collect({
                        case Failure(exception) => exception.getMessage
                      })
                      .mkString("\n")
              )
          )
      }
      // OLD VERSION WITH THROW
      //            lawnMowersInTextFile
      //                .grouped(2)
      //                .map(lawnMower => parseLawnMower(lawnMower, gridLimits))
      //                .collect({
      //                    case Failure(exception) => throw exception
      //                    case Success(lawnMower) => lawnMower
      //                })
      //                .toList
    }
  }

  private def parseLawnMower(
      lawnMowerInText: List[String],
      gridLimits: GridLimits
  ): Try[LawnMower] = {
    lawnMowerInText match {
      case mowerPosition :: mowerInstructions :: Nil =>
        val mowerPositionList = mowerPosition.split(" ").toList match {
          case x :: y :: direction :: Nil =>
            try {
              val xToInt = x.toInt // can throw NumberFormatException
              val yToInt = y.toInt // can throw NumberFormatException

              if (xToInt > gridLimits.x)
                Failure(
                    DonneesIncorectesException(
                        s"La position x de la tondeuse est supérieure à la limite de la grille. " +
                          s"Elle est de x=${xToInt.toString} et la limite de la grille en x est de ${gridLimits.x.toString}."
                    )
                )
              else if (yToInt > gridLimits.y)
                Failure(
                    DonneesIncorectesException(
                        s"La position y de la tondeuse est supérieure à la limite de la grille. " +
                          s"Elle est de y=${yToInt.toString} et la limite de la grille en y est de ${gridLimits.y.toString}."
                    )
                )
              else if (!CardinalDirection.values
                         .map(_.toString)
                         .contains(direction))
                Failure(
                    DonneesIncorectesException(
                        s"La direction de la tondeuse n'est pas correcte. " +
                          s"Elle doit être une des valeurs suivantes : ${CardinalDirection.values
                            .mkString(", ")}. " +
                          s"Elle est actuellement de '$direction.'"
                    )
                )
              else Success(List(x, y, direction))
            } catch {
              case _: NumberFormatException =>
                Failure(
                    DonneesIncorectesException(
                        s"La position x ou y de la tondeuse n'est pas un entier. " +
                          s"Elle est actuellement de x=$x et y=$y."
                    )
                )
            }

          case _ =>
            Failure(
                DonneesIncorectesException(
                    s"La position de la tondeuse n'est pas au bon format. Elle doit être de la forme x y orientation. " +
                      s"Exemple : 1 2 N. Elle est actuellement sous la forme : '$mowerPosition'"
                )
            )
        }
        val mowerInstructionsList = mowerInstructions.split("").toList match {
          case instructions
              if instructions
                .forall(Instruction.values.map(_.toString).contains) =>
            Success(instructions)
          case _ =>
            Failure(
                DonneesIncorectesException(
                    s"Les instructions de la tondeuse ne sont pas au bon format. " +
                      s"Elles doivent être une suite d'instructions. " +
                      s"Exemple : AADADAGGA. Elles sont actuellement sous la forme : '$mowerInstructions'"
                )
            )
        }
        mowerPositionList match {
          case Success(mowerPositionList) =>
            val mowerXYPose = mowerPositionList.headOption match {
              case Some(x) => x.toInt
              case None    => -1
            }

            mowerInstructionsList match {
              case Failure(exception) => Failure(exception)
              case Success(mowerInstructionsList) =>
                Success(
                    new LawnMower(
                        new GridPositionalInformation(
                            new Position(
                                mowerXYPose,
                                mowerPositionList(1).toInt
                            ),
                            CardinalDirection.withName(mowerPositionList(2))
                        ),
                        mowerInstructionsList
                          .map(instruction => Instruction.withName(instruction))
                    )
                )
            }
          case Failure(exception) => Failure(exception)
        }
      case _ =>
        Failure(
            DonneesIncorectesException(
                "Les informations et/ou les instructions d'une tondeuse sont incorrectement formatées. " +
                  "Elles doivent être de la forme x y orientation et une suite d'instructions. " +
                  "Exemple : \n'1 2 N\nAADADAGGA'\nL'une d'elle est actuellement sous la forme : '" + lawnMowerInText
                  .mkString("\n") + "'"
            )
        )
    }
  }
}
