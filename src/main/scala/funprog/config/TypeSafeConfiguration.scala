package funprog.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigException.{Missing, WrongType}

import scala.util.{Failure, Success, Try}

class TypeSafeConfiguration(config: Config) extends Configuration {

  def getString(key: String): Try[String] = {
    try {
      Success(config.getString(key))
    } catch {
      case _: Missing =>
        // if value is absent or null
        Failure(
            new Exception(
                "La clé '" + key + "' n'existe pas dans le fichier de configuration."
            )
        )
      case _: WrongType =>
        // if value is not convertible to a string
        Failure(
            new Exception(
                "La clé '" + key + "' n'est pas convertible en String."
            )
        )
      case _: Exception =>
        // if any other exception occurs
        Failure(
            new Exception(
                "Une erreur est survenue lors de la récupération de la clé '" + key + "' dans le fichier de configuration."
            )
        )
    }
  }
}
