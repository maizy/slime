package space.maizy.slime.test.app

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList
import space.maizy.slime.{ Candidate, Input, InputArg, InputDivider, InputLine }
import space.maizy.slime.completer.Completer
import space.maizy.slime.test.TestSpec

object SlimeTestApp {

  final private val LINE_SIZE = 100

  private val separator: NonEmptyList[String] = NonEmptyList.one("-" * LINE_SIZE)

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
        case InputArg(value, _) => "Arg: "
        case InputDivider(value, _) => "Div: "
      }) + s"'${arg.value}'$posText"
    }
  }

  private def describeCandidates(candidates: List[Candidate]): NonEmptyList[String] = {

    val initMax = candidates.map(_.init.mkString(" ").length).max
    val initPad = if (initMax == 0) 0 else initMax + 1
    val candidatesDescription = candidates.map {candidate =>
      val init = (if (candidate.init.isEmpty) {
        ""
      } else {
        candidate.init.mkString(" ") + " "
      }).padTo(initPad, ' ')
      val value = if (candidate.value != candidate.displayedValue) {
        s" (value: ${candidate.value})"
      } else {
        ""
      }
      val complete = if (candidate.complete) " !" else ""
      val description = candidate.description.map(d => s"\t$d").getOrElse("")
      (candidate.complete, s"$init→ '${candidate.displayedValue}'$value$complete$description")
    }
    val candidatesLinesAndFlag = NonEmptyList
      .fromList(candidatesDescription)
      .getOrElse(NonEmptyList.one((false, "<empty>")))

    val anyComplete = candidatesLinesAndFlag.foldLeft(false)((acc, i) => acc || i._1)

    val candidateLines = candidatesLinesAndFlag.map(_._2)
    if (anyComplete) {
      candidateLines ::: NonEmptyList.one("! - indicate that candidate is a complete (divider will be added after)")
    } else {
      candidateLines
    }
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

    val candidates = spec.generateCandidates(inputLine)

    val emptyLine = NonEmptyList.one("")

    val output = title("Completer") ::: describeCompleter(spec) ::: emptyLine :::
      title("Input Line") ::: describeInputLine(inputLine) ::: emptyLine :::
      title("Parsed Input") ::: describeInput(input) ::: emptyLine :::
      title("Candidates") ::: describeCandidates(candidates)

    output.toList.foreach(println)
  }


}
