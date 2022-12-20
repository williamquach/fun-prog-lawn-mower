package funprog.file_loader

class FileLoader {
    def loadFile(path: String): String = {
        val source = scala.io.Source.fromFile(path)
        try source.mkString finally source.close()
    }
}
