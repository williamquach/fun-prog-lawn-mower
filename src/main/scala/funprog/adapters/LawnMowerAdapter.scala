package funprog.adapters

import funprog.models._

import scala.util.Try

trait LawnMowerAdapter {
    def parseFileToDomain(parsedFile: String): Try[LawnMowingContext]
}
