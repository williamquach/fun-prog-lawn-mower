package fr.esgi.al.funprog

import progfun.adapters.LawnMowerTxtConverter
import progfun.file_loader.FileLoader

object Main extends App {
    println("Ici le programme principal")

    // Load file at ./../../resources/lawn-mower/example_1.json
    private val fileLoader = new FileLoader()
    private val fileContent = fileLoader.loadFile("src/main/resources/lawn-mower/example_1.txt")
    println(s"fileContent:\n$fileContent")

    // Adapting JSON to LawnMowingContext
    private val lawnMowerJSONAdapter = new LawnMowerTxtConverter()
    private val lawnMowingContext = lawnMowerJSONAdapter.convert(fileContent)
    println("Lawn mowing context:")
    println(lawnMowingContext)
}
