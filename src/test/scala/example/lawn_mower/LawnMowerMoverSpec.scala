package example.lawn_mower

import funprog.lawn_mower.LawnMowerMover
import funprog.models.{CardinalDirection, GridLimits, GridPositionalInformation, Instruction, LawnMower, LawnMowingContext, Position}
import org.scalatest.funsuite.AnyFunSuite

class LawnMowerMoverSpec extends AnyFunSuite{
  test("LawnMowerMover should a lawn mower according to the given instructions") {
    val instruction = List(Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A)
    val lawnMower = new funprog.lawn_mower.LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N))
    val lawnMowerMover = new LawnMowerMover()
    val expectedLawnMower = new LawnMower(new GridPositionalInformation(new Position(1, 3), CardinalDirection.N), List())
    assert(lawnMowerMover.moveLawnMower(instruction, lawnMower).toString == expectedLawnMower.gridPositionalInformation.toString)
  }

  test("LawnMowerMover should move a list of lawn mower according to the given instructions") {
    val lawnMowingContext = new LawnMowingContext(
      new GridLimits(5, 5),
      List(
        new LawnMower(
          new GridPositionalInformation(new Position(1, 2), CardinalDirection.N),
          List(Instruction.A, Instruction.G, Instruction.A, Instruction.D, Instruction.A)),
        new LawnMower(
          new GridPositionalInformation(new Position(3, 3), CardinalDirection.E),
          List(Instruction.A, Instruction.D, Instruction.D, Instruction.A, Instruction.G))))
    val lawnMowerMover = new LawnMowerMover()
    assert(lawnMowerMover.gridPositionalInformationListToString(
      lawnMowerMover.moveLawnMowers(lawnMowingContext.lawnMowers, lawnMowingContext)) == "0 4 N\n3 3 S\n")
  }
}
