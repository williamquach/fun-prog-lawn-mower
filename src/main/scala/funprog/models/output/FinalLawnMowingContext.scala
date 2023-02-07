package funprog.models.output

import funprog.models.lawn_mower.LawnMowingContext

class FinalLawnMowingContext(
    val lawnMowingContext: LawnMowingContext,
    val fileType: FileType,
    val outputFilePath: String
) {
  override def toString: String = {
    s"Final lawn mowing context output :\n" +
    s"\tFile type : ${fileType.toString}\n" +
    s"\tFile path : $outputFilePath\n" +
    s"\tLawn mowing context :\n${lawnMowingContext.toString}"
  }

  def setFileType(fileType: FileType): FinalLawnMowingContext = {
    new FinalLawnMowingContext(lawnMowingContext, fileType, outputFilePath)
  }

  def setFilePath(filePath: String): FinalLawnMowingContext = {
    new FinalLawnMowingContext(lawnMowingContext, fileType, filePath)
  }
}
