package progfun.parser.JSON

import play.api.libs.json._
import progfun.parser.Parser

class JsonParser extends Parser {
    def parse(input: String): JsonValue = {
        val json = Json.parse(input)
        jsonToScala(json)
    }

    private def jsonToScala(json: JsValue): JsonValue = json match {
        case JsObject(fields) => JsonObject(fields.map(f => (f._1, jsonToScala(f._2))).toMap)
        case JsArray(items) => JsonArray(items.map(jsonToScala).toArray.toList)
        case JsString(value) => JsonString(value)
        case JsNumber(value) => JsonNumber(value.toDouble)
        case JsNull => JsonNull
        case _ => JsonException("Unknown error")
    }
}
