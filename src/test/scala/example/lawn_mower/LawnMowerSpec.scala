package example.lawn_mower

import funprog.lawn_mower.LawnMower
import funprog.models.{CardinalDirection, GridLimits, GridPositionalInformation, Position}
import org.scalatest.funsuite.AnyFunSuite

class LawnMowerSpec extends AnyFunSuite {
  test("LawnMower should move_foreward: case N") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)).move_forward
    assert(lawnMower.toString == "1 3 N")
  }

  test("LawnMower should move_foreward: case S") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.S)).move_forward
    assert(lawnMower.toString == "1 1 S")
  }

  test("LawnMower should move_foreward: case E") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.E)).move_forward
    assert(lawnMower.toString == "2 2 E")
  }

  test("LawnMower should move_foreward: case W") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.W)).move_forward
    assert(lawnMower.toString == "0 2 W")
  }

  test("LawnMower should stay at the same position: case N") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)).move_forward
    assert(lawnMower.toString == "1 2 N")
  }

  test("LawnMower should stay at the same position: case S") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(1, 0), CardinalDirection.S)).move_forward
    assert(lawnMower.toString == "1 0 S")
  }

  test("LawnMower should stay at the same position: case E") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(2, 2), CardinalDirection.E)).move_forward
    assert(lawnMower.toString == "2 2 E")
  }

  test("LawnMower should stay at the same position: case W") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(0, 2), CardinalDirection.W)).move_forward
    assert(lawnMower.toString == "0 2 W")
  }

  test("LawnMower should rotate: case G") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)).rotate("G")
    assert(lawnMower.toString == "1 2 W")
  }

  test("LawnMower should rotate: case D") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)).rotate("D")
    assert(lawnMower.toString == "1 2 E")
  }

  test("LawnMower should move_forward with move(): case N") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)).move("A")
    assert(lawnMower.toString == "1 3 N")
  }

  test("LawnMower should move_foreward with move(): case S") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.S)).move("A")
    assert(lawnMower.toString == "1 1 S")
  }

  test("LawnMower should move_foreward with move(): case E") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.E)).move("A")
    assert(lawnMower.toString == "2 2 E")
  }

  test("LawnMower should move_foreward with move(): case W") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.W)).move("A")
    assert(lawnMower.toString == "0 2 W")
  }

  test("LawnMower should stay at the same position with move(): case N") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)).move("A")
    assert(lawnMower.toString == "1 2 N")
  }

  test("LawnMower should stay at the same position with move(): case S") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(1, 0), CardinalDirection.S)).move("A")
    assert(lawnMower.toString == "1 0 S")
  }

  test("LawnMower should stay at the same position with move(): case E") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(2, 2), CardinalDirection.E)).move("A")
    assert(lawnMower.toString == "2 2 E")
  }

  test("LawnMower should stay at the same position with move(): case W") {
    val lawnMower = new LawnMower(new GridLimits(2, 2), new GridPositionalInformation(new Position(0, 2), CardinalDirection.W)).move("A")
    assert(lawnMower.toString == "0 2 W")
  }

  test("LawnMower should rotate with move(): case G") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.E)).move("G")
    assert(lawnMower.toString == "1 2 N")
  }

  test("LawnMower should rotate with move(): case D") {
    val lawnMower = new LawnMower(new GridLimits(5, 5), new GridPositionalInformation(new Position(1, 2), CardinalDirection.S)).move("D")
    assert(lawnMower.toString == "1 2 W")
  }
}
