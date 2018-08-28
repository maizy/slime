package space.maizy.slime.completer

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import scala.util.matching.Regex
import cats.data.NonEmptyList
import space.maizy.slime.{ Candidate, InputLine, InputArgs, InputLineSplitter }

class RuleCompleter(splitRegexps: NonEmptyList[Regex] = NonEmptyList.one("\\s+".r)) extends Completer {
  override def splitLine(line: InputLine): InputArgs =
    InputLineSplitter.split(line, splitRegexps)

  override def generateCandidates(currentInput: InputLine): List[Candidate] = ???

  override def selectCandidate(currentInput: InputLine, candidate: Candidate): InputLine = ???
}
