package funprog.models.lawn_mower.position

class Position(val x: Int, val y: Int) {
  override def toString: String = {
    s"(x => ${x.toString}, y => ${y.toString})"
  }
}
