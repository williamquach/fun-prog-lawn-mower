package funprog.models

class GridLimits(val x: Int, val y: Int) {
    override def toString: String = {
        s"\t(x => ${x.toString}, y => ${y.toString})"
    }
}
