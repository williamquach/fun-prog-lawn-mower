package funprog

import com.typesafe.config.{Config, ConfigFactory}
import funprog.adapters.LawnMowerTxtConverter
import funprog.file_loader.FileLoader
import funprog.lawn_mower.LawnMowerMover
import funprog.output.FileType

object Main extends App {
    println("Beginning of the program...")
    println("====================")

    // File path through config file
    private val conf: Config = ConfigFactory.load()
    private val inputFilePath: String = conf.getString("application.input-file")

    // Ask user for output file type
    println("> In which type of file do you want to output your lawn mowers information ?")
    private val userWantOutputFileType = "json" // StdIn.readLine()
    private val outputFileType = FileType.withName(userWantOutputFileType)

    private val outputFilePath: String = conf.getString(s"application.output-${outputFileType.toString}-file")

    // Load file at ./../../resources/lawn-mower/example_1.json
    private val fileLoader = new FileLoader()
    private val fileContent = fileLoader.loadFile(inputFilePath)
    println(s"File content:\n$fileContent")

    println("====================")

    // Parsing text file to LawnMowingContext
    private val lawnMowerJSONAdapter = new LawnMowerTxtConverter()
    private val lawnMowingContext = lawnMowerJSONAdapter.convert(fileContent)
    println("Lawn mowing context :")
    println(lawnMowingContext)

    println("========= FILE RESULT ===========")
    private val lawnMowers = new LawnMowerMover
    private val movedLawnMowers = lawnMowers.moveLawnMowers(lawnMowingContext.lawnMowers, lawnMowingContext)
    println(lawnMowers.gridPositionalInformationListToString(movedLawnMowers))
    println("====================")

    //TODO : Call the file writer to write final result
    println(s"Output file path : $outputFilePath")

    println("====================")
    println("End of the program.")
}
