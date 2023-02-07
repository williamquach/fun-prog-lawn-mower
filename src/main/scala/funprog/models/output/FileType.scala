package funprog.models.output

sealed trait FileType

object FileType {
  def fromString(fileType: String): FileType = fileType match {
    case "json" => JSON
    case "yaml" => YAML
    case "csv"  => CSV
    case _      => Undefined
  }

  case object Undefined extends FileType

  case object JSON extends FileType

  case object YAML extends FileType

  case object CSV extends FileType
}
