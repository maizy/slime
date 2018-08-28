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
  def split(inputLine: InputLine, dividerRegexps: NonEmptyList[Regex]): InputArgs = {
    val finalRegExpPattern = dividerRegexps.map(r => s"(${r.pattern})")
      .reduceLeft((a, b) => s"$a|$b")
    val finalRegExp = finalRegExpPattern.r
    val inputLineText = inputLine.string
    val splittedByDivMatches = finalRegExp.findAllMatchIn(inputLineText).toList

    def buildPart(fromIndex: Int, toIndex: Int): InputArg = {
      val value = inputLineText.substring(fromIndex, toIndex)
      // FIXME: check cursor position
      InputArg(value, None)
    }

    @tailrec
    def extractArg(divMatches: List[Match], fromIndex: Int, acc: List[InputArg]): List[InputArg] = {
      divMatches match {
        case splitMatch :: tail =>
          extractArg(tail, splitMatch.end, buildPart(fromIndex, splitMatch.start) :: acc)
        case List() => buildPart(fromIndex, inputLineText.length) :: acc
      }
    }

    val extractedArgs = extractArg(splittedByDivMatches, 0, List.empty).reverse
    InputArgs(extractedArgs)
  }
}
