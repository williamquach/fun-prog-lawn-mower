package funprog.file_loader

import funprog.exceptions.DonneesIncorectesException

import scala.util.{Failure, Success, Try}

class FileLoader {
    def loadFile(path: String): Try[String] = {
        try {
            val source = scala.io.Source.fromFile(path)
            try Success(source.mkString) finally source.close()
        } catch {
            case _: Exception => Failure(DonneesIncorectesException("Impossible de charger le fichier"))
        }

    }
}
