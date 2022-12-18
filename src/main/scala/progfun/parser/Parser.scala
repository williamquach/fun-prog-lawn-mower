package progfun.parser

import progfun.parser.JSON.JsonValue

trait Parser {
    def parse(input: String): JsonValue
}
