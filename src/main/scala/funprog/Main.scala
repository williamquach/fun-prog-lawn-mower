package funprog

import com.typesafe.config.ConfigFactory
import funprog.adapters.{LawnMowerAdapter, LawnMowerTxtAdapter}
import funprog.config.TypeSafeConfiguration
import funprog.file_loader.FileLoader
import funprog.models.lawn_mower.movable.FinalLawnMowersHandler
import funprog.models.output.FileType.Undefined
import funprog.models.output.{FileType, FinalLawnMowingContext}

import scala.util.Success

//@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
object Main extends App {
    println("> Début du programme.")
    println("\n====================\n")

    private val configuration = new TypeSafeConfiguration(ConfigFactory.load())

    // File path through config file
    configuration.getString("application.input-file")
        .flatMap(inputFilePath => {
            val fileLoader = new FileLoader()
            fileLoader.loadFile(inputFilePath)
        })
        .flatMap(fileContent => {
            println(s"> Contenu de votre fichier :\n$fileContent")
            println("\n====================\n")

            // Parsing text file to LawnMowingContext
            val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()
            lawnMowerAdapter.parseFileToDomain(fileContent)
        })
        .flatMap(lawnMowingContext => {
            println("> Nous avons réussi à parser le fichier.")
            println("> Voici le contexte de(s) tondeuse(s) :")
            println(lawnMowingContext)
            println("\n====================\n")

            val lawnMowers = new FinalLawnMowersHandler
            // TODO : Change the following variable name when the moveLawnMowers function will return a LawnMowingContext
            val movedLawnMowers = lawnMowers.moveLawnMowers(lawnMowingContext.lawnMowers, lawnMowingContext)
            println("> Voici le contexte de(s) tondeuse(s) après mouvement(s) :")
            println(lawnMowers.gridPositionalInformationListToString(movedLawnMowers))
            println("\n====================\n")

            val finalLawnMowingContext: FinalLawnMowingContext = new FinalLawnMowingContext(
                lawnMowingContext,
                Undefined,
                "",
            )

            Success(finalLawnMowingContext)
        })
        .flatMap((lawnMowingContext: FinalLawnMowingContext) => {
            // Ask user for output file type
            println("> Quel type de de sortie souhaitez-vous pour extraire les informations des tondeuses ?")
            val userWantOutputFileType = "json" // StdIn.readLine()
            val outputFileType = FileType.fromString(userWantOutputFileType)

            val finalLawnMowingContextWithFileType = lawnMowingContext.setFileType(outputFileType)

            configuration.getString(s"application.output-${outputFileType.toString.toLowerCase()}-file").flatMap(
                outputFilePath => {
                    println(s"> Vous avez choisi le type de sortie ${outputFileType.toString}.")
                    println(s"> Le fichier de sortie sera $outputFilePath.")
                    println("\n====================\n")
                    Success(finalLawnMowingContextWithFileType.setFilePath(outputFilePath))
                }
            )
        })
        .flatMap((finalLawnMowingContextWithFilePath: FinalLawnMowingContext) => {
            //TODO : Call the file writer to write final result

            println("> Les informations des tondeuses ont bien été extraites.")
            println(s"> Chemin du fichier de sortie : ${finalLawnMowingContextWithFilePath.outputFilePath}")

            println("\n====================\n")
            println("> Fin du programme.")
            Success("OK")
        })
        .failed
        .foreach(throwable => {
            println("> Nous n'avons pas réussi à parser le fichier.")
            println("> Pour la raison suivante : \n- " + throwable.getMessage)
            println("\n====================\n")
            println("> Fin du programme.")
        })

}
