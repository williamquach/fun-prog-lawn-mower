package fr.esgi.al.funprog

import progfun.adapters.LawnMowerJSONAdapter
import progfun.file_loader.FileLoader
import progfun.parser.JSON.JsonParser

object Main extends App {
    println("Ici le programme principal")

    // Load file at ./../../resources/lawn-mower/example_1.json
    private val fileLoader = new FileLoader()
    private val fileContent = fileLoader.loadFile("src/main/resources/lawn-mower/example_1.json")
    println(s"fileContent: $fileContent")

    // Parse fileContent in JSON
    private val jsonParser = new JsonParser()
    private val jsonValue = jsonParser.parse(fileContent)
    println(jsonValue)

    // Adapting JSON to LawnMowingContext
    try {
        val lawnMowerJSONAdapter = new LawnMowerJSONAdapter()
        val lawnMowingContext = lawnMowerJSONAdapter.convert(jsonValue)
        println(lawnMowingContext)
    } catch {
        case e: Exception => println(e)
    }
}
