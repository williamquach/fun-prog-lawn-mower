package funprog.adapters

import funprog.models.lawn_mower.LawnMowingContext

import scala.util.Try

trait LawnMowerAdapter {
  def parseFileToDomain(parsedFile: String): Try[LawnMowingContext]
}
