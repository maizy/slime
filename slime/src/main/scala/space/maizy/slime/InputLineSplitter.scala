package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import scala.annotation.tailrec
import scala.util.matching.Regex
import scala.util.matching.Regex.Match
import cats.data.NonEmptyList


object InputLineSplitter {

  private def getValueAndCursor(inputLine: InputLine, fromIndex: Int, toIndex: Int): (String, Option[Int]) = {
    val value = inputLine.string.substring(fromIndex, toIndex)
    val position = inputLine.cursorPosition
    val cursor = if (position > fromIndex && position <= toIndex) {
      Some(position - fromIndex)
    } else {
      None
    }
    (value, cursor)
  }

  private def buildArg(inputLine: InputLine, fromIndex: Int, toIndex: Int): InputArg = {
    val (value, cursor) = getValueAndCursor(inputLine, fromIndex, toIndex)
    InputArg(value, cursor)
  }

  private def buildDiv(inputLine: InputLine, fromIndex: Int, toIndex: Int): InputDivider = {
    val (value, cursor) = getValueAndCursor(inputLine, fromIndex, toIndex)
    InputDivider(value, cursor)
  }

  @tailrec
  private def extractPartIter(
      inputLine: InputLine,
      divMatches: List[Match],
      argFromIndex: Int,
      acc: List[InputPart]): List[InputPart] = {

    divMatches match {
      case splitMatch :: tail =>

        // if string not start with divider, add empty init divider
        val initDiv = if (argFromIndex == 0 && splitMatch.start != 0) {
          List(buildDiv(inputLine, 0, 0))
        } else {
          List()
        }

        val arg = if (splitMatch.start != 0) {
          List(buildArg(inputLine, argFromIndex, splitMatch.start))
        } else {
          List()
        }

        val div = buildDiv(inputLine, splitMatch.start, splitMatch.end)

        extractPartIter(
          inputLine,
          tail,
          argFromIndex = splitMatch.end,
          acc = (div :: (arg ++ initDiv)) ++ acc
        )

      case List() =>
        // if string not end with divider)
        if (argFromIndex != inputLine.length) {
          buildArg(inputLine, argFromIndex, inputLine.length) :: acc
        } else {
          acc
        }
    }
  }

  def split(inputLine: InputLine, dividerRegexps: NonEmptyList[Regex]): Input = {
    val finalRegExpPattern = dividerRegexps.map(r => s"(${r.pattern})")
      .reduceLeft((a, b) => s"$a|$b")
    val finalRegExp = finalRegExpPattern.r
    val splittedByDivMatches = finalRegExp.findAllMatchIn(inputLine.string).toList

    def emptyParts = NonEmptyList.of(buildDiv(inputLine, 0, 0))
    val extractedParts = if (inputLine.length == 0) {
      emptyParts
    } else {
      NonEmptyList
        .fromList(extractPartIter(inputLine, splittedByDivMatches, 0, List.empty).reverse)
        .getOrElse(emptyParts)
    }
    Input(extractedParts)
  }
}
