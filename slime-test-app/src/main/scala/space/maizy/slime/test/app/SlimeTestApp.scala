package space.maizy.slime.test.app

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList
import space.maizy.slime.completer.Completer
import space.maizy.slime.test.TestSpec
import space.maizy.slime.{ Candidate, CandidateChain, Input, InputArg, InputDivider, InputLine, MatchedValue }

/**
 * TODO: add ansi colors
 */
object SlimeTestApp {

  final private val LINE_SIZE = 100
  final private val COMPLETE_MARK = "!"

  private val separator: NonEmptyList[String] = NonEmptyList.one("-" * LINE_SIZE)

  private def describeMatchedValue(value: MatchedValue): String = describeMatchedValue(value.value)
  private def describeMatchedValue(value: String): String = s"[$value]"

  private def describeCandidate(value: Candidate): String = describeCandidate(value.value)
  private def describeCandidate(value: String): String = s"{$value}"

  private def describeCompleter(completer: Completer): NonEmptyList[String] = {
    // TODO: describe rules
    NonEmptyList.one(
      s"Class: ${completer.getClass.getName}"
    )
  }

  private def describeInputLine(inputLine: InputLine): NonEmptyList[String] = {
    NonEmptyList.of(
      inputLine.string,
      " " * inputLine.cursorPosition + "↖ cursor"
    )
  }

  private def describeInput(input: Input): NonEmptyList[String] = {
    input.inputParts.map { arg =>
      val posText = arg.cursorPosition.map(p => s" (active, cursor @$p)").getOrElse("")
      (arg match {
        case _: InputArg => "Arg: "
        case _: InputDivider => "Div: "
      }) + s"'${arg.value}'$posText"
    }
  }

  private def describeCandidateChainElement(element: CandidateChain.Element): List[String] = element match {
    case Left(value: MatchedValue) => List(describeMatchedValue(value))
    case Right(candidateList: List[Candidate]) => candidateList.map { candidate =>
      val complete = if (candidate.complete) COMPLETE_MARK else ""
      s"${describeCandidate(candidate)}$complete"
    }
  }

  private def describeCandidatesChains(candidatesChain: List[CandidateChain]): NonEmptyList[String] = {

    val candidatesChainsDescriptionLines: List[String] = candidatesChain
      .zipWithIndex
      .flatMap { case (candidateChain, index) =>
        val elementsDescription = candidateChain.elements
          .map(describeCandidateChainElement)
          .foldLeft((List.empty[String], 0)) {
            case ((acc, shift), candidateValues) =>
              val pad = " " * shift
              val maxLen = candidateValues.map(_.length).max
              (
                acc ::: candidateValues.map { value =>
                  s"$pad $value"
                },
                shift + maxLen + 1
              )
          }._1
        List(s"---- chain ${index + 1} ----") ::: elementsDescription
      }
    NonEmptyList
      .fromList(candidatesChainsDescriptionLines)
      .getOrElse(NonEmptyList.one("<empty>")
      ) ::: NonEmptyList.of(
      "",
      s"$COMPLETE_MARK - indicate that candidate is a complete (divider will be added after)",
      s"${describeMatchedValue("matched_value")} - matched value",
      s"${describeCandidate("candidate")} - generated candidates"
    )
  }

  private def title(text: String): NonEmptyList[String] = {
    val before = "---  "
    val afterFill = LINE_SIZE - before.length - text.length - 2
    val after = if (afterFill > 0) "-" * afterFill else ""
    separator ::: NonEmptyList.of(s"$before$text  $after") ::: separator
  }

  private def firstIndexOf(text: String, substring: String): Option[Int] = {
    val jIndex = text.indexOf(substring)
    if (jIndex == -1) { // 🤢
      None
    } else {
      Some(jIndex)
    }
  }

  def main(args: Array[String]): Unit = {
    val inputArg = args.toList.reduce(_ + " " + _ )
    val cursorPosition: Int = firstIndexOf(inputArg, "|").getOrElse(inputArg.length)
    val inputText = inputArg.replaceFirst("\\|", "")

    val spec = TestSpec.fakeCompleter

    val inputLine = InputLine(inputText, cursorPosition)
    val input = spec.splitLine(inputLine)

    val candidates = spec.generateCandidatesChains(inputLine)

    val emptyLine = NonEmptyList.one("")

    val output = title("Completer") ::: describeCompleter(spec) ::: emptyLine :::
      title("Input Line") ::: describeInputLine(inputLine) ::: emptyLine :::
      title("Parsed Input") ::: describeInput(input) ::: emptyLine :::
      title("Candidates") ::: describeCandidatesChains(candidates)

    output.toList.foreach(println)
  }


}
