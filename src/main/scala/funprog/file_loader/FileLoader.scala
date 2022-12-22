package funprog.file_loader

import funprog.exceptions.DonneesIncorectesException

class FileLoader {
    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    def loadFile(path: String): String = {
        try {
            val source = scala.io.Source.fromFile(path)
            try source.mkString finally source.close()
        } catch {
            case _: Exception => throw DonneesIncorectesException("Impossible de charger le fichier")
        }

    }
}
