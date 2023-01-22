package funprog.config

import scala.util.Try

trait Configuration {
    def getString(key: String): Try[String]
}
