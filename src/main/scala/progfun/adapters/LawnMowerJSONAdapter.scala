package progfun.adapters

import progfun.models.{CardinalDirection, GridLimits, GridPositionalInformation, LawnMower, LawnMowingContext, Position}
import progfun.parser.JSON.{JsonArray, JsonNumber, JsonObject, JsonString, JsonValue}

class LawnMowerJSONAdapter {
    private def getGridLimits(limit: JsonValue): GridLimits = {
        limit match {
            case JsonObject(fields) => {
                val width = fields("x") match {
                    case JsonNumber(width) => width.toInt
                }
                val height = fields("y") match {
                    case JsonNumber(height) => height.toInt
                }

                new GridLimits(width, height)
            }
        }
    }

    private def getLawnMowers(mowers: JsonValue): List[LawnMower] = {
        mowers match {
            case JsonArray(mowersArray) => mowersArray.map {
                case JsonObject(mowerFields) => {
                    val x = mowerFields("x") match {
                        case JsonNumber(x) => x.toInt
                    }
                    val y = mowerFields("y") match {
                        case JsonNumber(y) => y.toInt
                    }
                    val orientation = mowerFields("orientation") match {
                        case JsonString(orientation) => orientation
                    }

                    new LawnMower(
                        new GridPositionalInformation(
                            new Position(x, y),
                            CardinalDirection.withName(orientation)
                        ),
                        List()
                    )
                }
            }
        }
    }

    def convert(parsedFile: JsonValue): LawnMowingContext = {
        parsedFile match {
            case JsonObject(fields) => {
                val gridLimits: GridLimits = getGridLimits(fields("limite"))
                val lawnMowers: List[LawnMower] = getLawnMowers(fields("tondeuses"))

                new LawnMowingContext(gridLimits = gridLimits, lawnMowers = lawnMowers)
            }
//            case _ => throw new Exception("Invalid JSON file format for lawn mowing context")
        }
    }
}
