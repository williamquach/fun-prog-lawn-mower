package funprog

import com.typesafe.config.{Config, ConfigFactory}
import funprog.adapters.LawnMowerTxtConverter
import funprog.file_loader.FileLoader
import funprog.lawn_mower.LawnMowerMover
import funprog.output.FileType

import scala.util.{Failure, Success}

object Main extends App {
    println("> Début du programme.")
    println("====================")

    // File path through config file
    private val conf: Config = ConfigFactory.load()
    private val inputFilePath: String = conf.getString("application.input-file")

    // Ask user for output file type
    println("> Quel type de de sortie souhaitez-vous pour extraire les informations des tondeuses ?")
    private val userWantOutputFileType = "json" // StdIn.readLine()
    private val outputFileType = FileType.withName(userWantOutputFileType)
    private val outputFilePath: String = conf.getString(s"application.output-${outputFileType.toString}-file")

    println("====================")
    private val fileLoader = new FileLoader()
    private val fileContent = fileLoader.loadFile(inputFilePath)
    println(s"> Contenu de votre fichier :\n$fileContent")
    println("====================")

    // Parsing text file to LawnMowingContext
    private val lawnMowerJSONAdapter = new LawnMowerTxtConverter()
    private val lawnMowingContext = lawnMowerJSONAdapter.parseFileToDomain(fileContent)

    // Use Success and Failure from lawnMowingContext to handle errors
    lawnMowingContext match {
        case Failure(exception) =>
            println("> Nous n'avons pas réussi à parser le fichier.")
            println("> Pour la raison suivante : \n- " + exception.getMessage)
            System.exit(1)

        case Success(lawnMowingContext) =>
            println("> Nous avons réussi à parser le fichier.")
            println("> Voici le contexte de(s) tondeuse(s) :")
            println(lawnMowingContext)
            println("====================")

            val lawnMowers = new LawnMowerMover
            val movedLawnMowers = lawnMowers.moveLawnMowers(lawnMowingContext.lawnMowers, lawnMowingContext)
            println("> Voici le contexte de(s) tondeuse(s) après mouvement(s) :")
            println(lawnMowers.gridPositionalInformationListToString(movedLawnMowers))
            println("====================")

            //TODO : Call the file writer to write final result

            println("> Les informations des tondeuses ont bien été extraites.")
            println(s"> Chemin du fichier de sortie : $outputFilePath")

            println("====================")
            println("> Fin du programme.")
    }
}
