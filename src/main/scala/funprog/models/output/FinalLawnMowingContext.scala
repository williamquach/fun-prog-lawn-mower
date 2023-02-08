package funprog.models.output

import funprog.models.lawn_mower.FinalLawnMowingContextWrites.toCSV
import funprog.models.lawn_mower.FinalLawnMowingContextWrites.finalLawnMowingContextWrites
import play.api.libs.json.Json

import java.io.{File, FileWriter}

class FinalLawnMowingContext(
    val finalLawnMowingContext: funprog.models.lawn_mower.FinalLawnMowingContext,
    val fileType: FileType,
    val outputFilePath: String
) {
  override def toString: String = {
    s"Final lawn mowing context output :\n" +
    s"\tFile type : ${fileType.toString}\n" +
    s"\tFile path : $outputFilePath\n" +
    s"\tLawn mowing context :\n${finalLawnMowingContext.toString}"
  }

  def setFileType(fileType: FileType): FinalLawnMowingContext = {
    new FinalLawnMowingContext(finalLawnMowingContext, fileType, outputFilePath)
  }

  def setFilePath(filePath: String): FinalLawnMowingContext = {
    new FinalLawnMowingContext(finalLawnMowingContext, fileType, filePath)
  }

  def writeFile(): Unit = {
    fileType match {
      case FileType.JSON      => writeJSONFile()
      case FileType.CSV       => writeCVSFile()
      case FileType.YAML      => // TODO : write YAML file
      case FileType.Undefined =>
    }
  }

  def writeCVSFile(): Unit = {
    val header =
      "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n"
    val rows = toCSV(finalLawnMowingContext)

    val fileWriter = new FileWriter(
        new File(outputFilePath)
    )
    fileWriter.write(header)
    fileWriter.write(rows)
    fileWriter.close()

    println("CSV file written successfully")
  }

  def writeJSONFile(): Unit = {
    val fileWriter = new FileWriter(
        new File(outputFilePath)
    )
    fileWriter.write(Json.prettyPrint(Json.toJson(finalLawnMowingContext)))
    fileWriter.close()

    println("JSON file written successfully")
  }
}
