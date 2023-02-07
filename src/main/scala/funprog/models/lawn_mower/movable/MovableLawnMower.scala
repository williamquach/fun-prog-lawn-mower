package funprog.models.lawn_mower.movable

import funprog.models.lawn_mower.grid.GridLimits
import funprog.models.lawn_mower.position.{
  CardinalDirection,
  GridPositionalInformation,
  Position
}

class MovableLawnMower(
    limits: GridLimits,
    lawnMowerInfo: GridPositionalInformation
) {
  def move(action: String): MovableLawnMower = action match {
    case "G" => this.rotate("G")
    case "D" => this.rotate("D")
    case "A" => this.move_forward
    case _   => this
  }

  def move_forward: MovableLawnMower = {
    val (newX, newY) = lawnMowerInfo.direction.toString match {
      case "N" =>
        (this.lawnMowerInfo.position.x, this.lawnMowerInfo.position.y + 1)
      case "E" =>
        (this.lawnMowerInfo.position.x + 1, this.lawnMowerInfo.position.y)
      case "W" =>
        (this.lawnMowerInfo.position.x - 1, this.lawnMowerInfo.position.y)
      case "S" =>
        (this.lawnMowerInfo.position.x, this.lawnMowerInfo.position.y - 1)
      case _ => (this.lawnMowerInfo.position.x, this.lawnMowerInfo.position.y)
    }
    if (newX >= 0 && newX <= this.limits.x && newY >= 0 && newY <= this.limits.y) {
      new MovableLawnMower(
          limits,
          new GridPositionalInformation(
              new Position(newX, newY),
              lawnMowerInfo.direction
          )
      )
    } else {
      this
    }
  }

  def rotate(action: String): MovableLawnMower = {
    val newDirection = (lawnMowerInfo.direction, action) match {
      case (CardinalDirection.N, "G") => CardinalDirection.W
      case (CardinalDirection.E, "G") => CardinalDirection.N
      case (CardinalDirection.W, "G") => CardinalDirection.S
      case (CardinalDirection.S, "G") => CardinalDirection.E
      case (CardinalDirection.N, "D") => CardinalDirection.E
      case (CardinalDirection.E, "D") => CardinalDirection.S
      case (CardinalDirection.W, "D") => CardinalDirection.N
      case (CardinalDirection.S, "D") => CardinalDirection.W
      case _                          => lawnMowerInfo.direction
    }
    new MovableLawnMower(
        limits,
        new GridPositionalInformation(lawnMowerInfo.position, newDirection)
    )
  }

  def lawnMowerToGridPositionalInformation(
      lawnMower: MovableLawnMower
  ): GridPositionalInformation = {
    new GridPositionalInformation(lawnMower.getPosition, lawnMower.getDirection)
  }

  def getPosition: Position = {
    this.lawnMowerInfo.position
  }

  def getDirection: CardinalDirection.Direction = {
    this.lawnMowerInfo.direction
  }

  override def toString: String = {
    lawnMowerInfo.position.x.toString + " " + lawnMowerInfo.position.y.toString + " " + lawnMowerInfo.direction.toString
  }
}
