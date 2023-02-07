package funprog.models.lawn_mower.position

import funprog.models.lawn_mower.position.CardinalDirection.Direction

class GridPositionalInformation(
    val position: Position,
    val direction: Direction
) {
  override def toString: String = {
    s"\t\tPosition => ${position.toString}\n" +
    s"\t\tDirection => ${direction.toString}"
  }
}
