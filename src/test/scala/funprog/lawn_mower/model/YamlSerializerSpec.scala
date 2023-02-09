package funprog.lawn_mower.model

import funprog.models.YamlSerializer.{serializeDirection, serializeFinalLawnMowingContext, serializeGridLimits, serializeGridPositionalInformation, serializeInstructions, serializeLawnMowers, serializePosition}
import funprog.models.lawn_mower.{FinalLawnMower, FinalLawnMowingContext}
import funprog.models.lawn_mower.grid.GridLimits
import funprog.models.lawn_mower.movable.Instruction
import funprog.models.lawn_mower.position.{CardinalDirection, GridPositionalInformation, Position}
import org.scalatest.funsuite.AnyFunSuite

class YamlSerializerSpec extends AnyFunSuite{

  test("serializePosition should return the correct string representation of a position") {
    val position = new Position(1, 2)
    val expected =
      "\n    point:" +
        "\n        x: 1" +
        "\n        y: 2"

    val result = serializePosition(position)

    assert(result == expected)
  }


  test("serializeGridLimits should return the correct string representation of grid limits") {
    val gridLimits = new GridLimits(3, 4)
    val expected =
      "\n  x: 3" +
        "\n  y: 4"

    val result = serializeGridLimits(gridLimits)

    assert(result == expected)
  }

  test("serializeDirection should return the correct string representation of a direction") {
    val direction = CardinalDirection.N
    val expected = "N"

    val result = serializeDirection(direction)

    assert(result == expected)
  }

  test("serializeInstructions should return the correct string representation of a list of instructions") {
    val instructions = List(Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A)
    val expected =
      "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A"

    val result = serializeInstructions(instructions)

    assert(result == expected)
  }

  test("serializeGridPositionalInformation should return the correct string representation of grid positional information") {
    val gridPositionalInformation = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
    val expected =
      "\n    point:" +
        "\n        x: 1" +
        "\n        y: 2" +
        "\n    direction: N"

    val result = serializeGridPositionalInformation(gridPositionalInformation)

    assert(result == expected)
  }

  test("serializeLawnMowers should return the correct string representation of a list of lawn mowers") {
    val lawnMowers = List(
      new FinalLawnMower(
        start = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
        instructions = List(Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A),
        end = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
      ),
      new FinalLawnMower(
        start = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
        instructions = List(Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A),
        end = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
      )
    )
    val expected =
      "\n- debut:" +
        "\n    point:" +
        "\n        x: 1" +
        "\n        y: 2" +
        "\n    direction: N" +
        "\n  instructions:" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  fin:" +
        "\n    point:" +
        "\n        x: 1" +
        "\n        y: 2" +
        "\n    direction: N"+
        "\n- debut:" +
        "\n    point:" +
        "\n        x: 1" +
        "\n        y: 2" +
        "\n    direction: N" +
        "\n  instructions:" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  - G" +
        "\n  - A" +
        "\n  fin:" +
        "\n    point:" +
        "\n        x: 1" +
        "\n        y: 2" +
        "\n    direction: N"

    val result = serializeLawnMowers(lawnMowers)

    assert(result == expected)
  }

  test("serializeFinalLawnMowerContext should return the correct string representation of a final lawn mower context") {
    val finalLawnMowingContext = new FinalLawnMowingContext(
      new GridLimits(5, 5),
      List(
        new FinalLawnMower(
          start = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
          instructions = List(Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A),
          end = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
        ),
        new FinalLawnMower(
          start = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
          instructions = List(Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A),
          end = new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
        )
      )
    )
    val expected =
      "limit:" +
      "\n  x: 5" +
      "\n  y: 5" +
      "\ntondeuses:" +
      "\n- debut:" +
      "\n    point:" +
      "\n        x: 1" +
      "\n        y: 2" +
      "\n    direction: N" +
      "\n  instructions:" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  fin:" +
      "\n    point:" +
      "\n        x: 1" +
      "\n        y: 2" +
      "\n    direction: N"+
      "\n- debut:" +
      "\n    point:" +
      "\n        x: 1" +
      "\n        y: 2" +
      "\n    direction: N" +
      "\n  instructions:" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  - G" +
      "\n  - A" +
      "\n  fin:" +
      "\n    point:" +
      "\n        x: 1" +
      "\n        y: 2" +
      "\n    direction: N"

    val result = serializeFinalLawnMowingContext(finalLawnMowingContext)

    assert(result == expected)
  }



  }
