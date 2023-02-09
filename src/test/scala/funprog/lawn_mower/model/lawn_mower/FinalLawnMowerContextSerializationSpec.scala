package funprog.lawn_mower.model.lawn_mower

import funprog.models.lawn_mower.FinalLawnMowingContextWrites.finalLawnMowingContextWrites
import funprog.models.lawn_mower.{FinalLawnMower, FinalLawnMowingContext, FinalLawnMowingContextWrites}
import funprog.models.lawn_mower.grid.GridLimits
import funprog.models.lawn_mower.movable.Instruction
import funprog.models.lawn_mower.position.{CardinalDirection, GridPositionalInformation, Position}
import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.Json


class FinalLawnMowerContextSerializationSpec extends AnyFunSuite{

  test("toCSV method returns the expected string") {
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

    val expectedCSV = "1;1;2;N;1;2;N;AG\n2;1;2;N;1;2;N;AG\n"
    val result = FinalLawnMowingContextWrites.toCSV(finalLawnMowingContext)

    assert(result == expectedCSV)
  }

  test("toJson method returns the expected Json.obj") {
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

    val expectedJson = """{"limite":{"x":5,"y":5},"tondeuses":[{"start":{"point":{"x":1,"y":2},"direction":"N"},"instructions":["A","G"],"end":{"point":{"x":1,"y":2},"direction":"N"}},{"start":{"point":{"x":1,"y":2},"direction":"N"},"instructions":["A","G"],"end":{"point":{"x":1,"y":2},"direction":"N"}}]}"""
    val result = Json.toJson(finalLawnMowingContext)

    assert(result.toString == expectedJson)

  }

}
