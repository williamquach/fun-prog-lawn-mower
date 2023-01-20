package funprog.lawn_mower.adapters

import funprog.adapters.{LawnMowerAdapter, LawnMowerTxtAdapter}
import funprog.exceptions.DonneesIncorectesException
import funprog.models._
import org.scalatest.funsuite.AnyFunSuite

import scala.util.Failure

@SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
class LawnMowerTxtAdapterSpec extends AnyFunSuite {
    // Text look like :
    //    5 5 -> Grid context
    //    1 2 N -> Lawn mower 1 position
    //    GAGAGAGAA -> Lawn mower 1 instructions
    //    3 3 E -> Lawn mower 2 position
    //    AADAADADDA -> Lawn mower 2 instructions

    test("Should parse a correct txt file into a LawnMowingContext") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        lawnMowerAdapter.parseFileToDomain("5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA")
            .map(lawnMowingContext => {
                assert(
                    lawnMowingContext.gridLimits.toString == "5 5"
                )
                assert(
                    lawnMowingContext.lawnMowers(0).gridPositionalInformation == new GridPositionalInformation(new Position(1, 2), CardinalDirection.N)
                )
                assert(
                    lawnMowingContext.lawnMowers(0).instructions == List(Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.G, Instruction.A, Instruction.A)
                )
                assert(
                    lawnMowingContext.lawnMowers(1).gridPositionalInformation == new GridPositionalInformation(new Position(3, 3), CardinalDirection.E)
                )
                assert(
                    lawnMowingContext.lawnMowers(1).instructions == List(Instruction.A, Instruction.A, Instruction.D, Instruction.A, Instruction.A, Instruction.D, Instruction.A, Instruction.D, Instruction.D, Instruction.A)
                )
            })
    }

    test("Should return a Failure when grid context is missing y") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }

    test("Should return a Failure when lawn mower position doesn't have direction") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("5 5\n1 2\nGAGAGAGAA")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }

    test("Should return a Failure when lawn mower position is missing") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("5 5\nGAGAGAGAA")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }

    test("Should return a Failure when lawn mower instructions are incorrect (not of enum Instruction)") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("5 5\n1 2 N\nGAGAGAGAAX")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }

    test("Should return a Failure when lawn mower instructions are missing") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("5 5\n1 2 N\n")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }

    test("Should return a Failure when no lawn mower is present") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("5 5\n")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }

    test("Should return a Failure when everything is missing") {
        val lawnMowerAdapter: LawnMowerAdapter = new LawnMowerTxtAdapter()

        val lawnMowingContext = lawnMowerAdapter.parseFileToDomain("")
        assert(lawnMowingContext.asInstanceOf[Failure[DonneesIncorectesException]].isFailure)
    }
}
