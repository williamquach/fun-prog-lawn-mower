package funprog.models

class LawnMowingContext(val gridLimits: GridLimits, val lawnMowers: List[LawnMower]) {
    override def toString: String = {
        s"Grid limits :\n${gridLimits.toString}\n" +
            s"Lawn mowers :\n${lawnMowers.map(lawnMower => lawnMower.toString).mkString("\n")}"
    }
}