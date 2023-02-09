package funprog.lawn_mower.model.output

import java.nio.file.{Files, Paths}
import funprog.models.lawn_mower.{FinalLawnMower, FinalLawnMowingContext}
import funprog.models.lawn_mower.grid.GridLimits
import funprog.models.lawn_mower.movable.Instruction
import funprog.models.lawn_mower.position.{CardinalDirection, GridPositionalInformation, Position}
import funprog.models.output.FileType
import org.scalatest.funsuite.AnyFunSuite


class WriteFileTest extends AnyFunSuite {

  val outputFilePath = "src/test/resources/tmp/output.txt"
  val finalLawnMowingContext = new FinalLawnMowingContext(
    new GridLimits(5, 5),
    List(
      new FinalLawnMower(
        start = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
        instructions = List(Instruction.A, Instruction.G),
        end = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
      ),
      new FinalLawnMower(
        start = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
        instructions = List(Instruction.A, Instruction.G),
        end = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
      )
    )
  )
  val finalLawnMowingContextOutput = new funprog.models.output.FinalLawnMowingContext(finalLawnMowingContext, FileType.fromString("json"), outputFilePath)


  test("Write CSV file") {
    val finalLawnMowingContextCSV = finalLawnMowingContextOutput.setFileType(FileType.CSV)
    finalLawnMowingContextCSV.writeFile()

    val fileContent = new String(Files.readAllBytes(Paths.get(outputFilePath)))
    assert(fileContent.startsWith("numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n1;1;2;N;1;2;N;AG\n2;1;2;N;1;2;N;AG\n"))
    Paths.get(outputFilePath).toFile.delete()
  }

  test("Write JSON file") {
    val finalLawnMowingContextJSON = finalLawnMowingContextOutput.setFileType(FileType.JSON)
    finalLawnMowingContextJSON.writeFile()

    val fileContent = new String(Files.readAllBytes(Paths.get(outputFilePath)))
    assert(fileContent.startsWith("{\n  \"limite\" : {\n    \"x\" : 5,\n    \"y\" : 5\n  },\n  \"tondeuses\" : [ {\n    \"start\" : {\n      \"point\" : {\n        \"x\" : 1,\n        \"y\" : 2\n      },\n      \"direction\" : \"N\"\n    },\n    \"instructions\" : [ \"A\", \"G\" ],\n    \"end\" : {\n      \"point\" : {\n        \"x\" : 1,\n        \"y\" : 2\n      },\n      \"direction\" : \"N\"\n    }\n  }, {\n    \"start\" : {\n      \"point\" : {\n        \"x\" : 1,\n        \"y\" : 2\n      },\n      \"direction\" : \"N\"\n    },\n    \"instructions\" : [ \"A\", \"G\" ],\n    \"end\" : {\n      \"point\" : {\n        \"x\" : 1,\n        \"y\" : 2\n      },\n      \"direction\" : \"N\"\n    }\n  } ]\n}"))
    Paths.get(outputFilePath).toFile.delete()
  }

  test("Write YAML file") {
    val finalLawnMowingContextYAML = finalLawnMowingContextOutput.setFileType(FileType.YAML)
    finalLawnMowingContextYAML.writeFile()

    val fileContent = new String(Files.readAllBytes(Paths.get(outputFilePath)))
    assert(fileContent.startsWith("limit:\n  x: 5\n  y: 5\ntondeuses:\n- debut:\n    point:\n        x: 1\n        y: 2\n    direction: N\n  instructions:\n  - A\n  - G\n  fin:\n    point:\n        x: 1\n        y: 2\n    direction: N\n- debut:\n    point:\n        x: 1\n        y: 2\n    direction: N\n  instructions:\n  - A\n  - G\n  fin:\n    point:\n        x: 1\n        y: 2\n    direction: N"))
    Paths.get(outputFilePath).toFile.delete()
  }

}

