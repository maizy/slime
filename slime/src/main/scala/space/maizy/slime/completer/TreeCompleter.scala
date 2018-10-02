package space.maizy.slime.completer

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import scala.util.matching.Regex
import cats.data.NonEmptyList
import space.maizy.slime.candidate_generator_tree.CandidateGeneratorTree
import space.maizy.slime.{ CandidateChain, Input, InputLine, InputLineSplitter }


class TreeCompleter(
    candidatesTree: CandidateGeneratorTree.Type,
    splitRegexps: NonEmptyList[Regex] = NonEmptyList.one("\\s+".r))
  extends Completer {

  override def splitLine(line: InputLine): Input =
    InputLineSplitter.split(line, splitRegexps)

  override def generateCandidatesChains(currentInput: InputLine): List[CandidateChain] = ???

  override def selectCandidateChain(currentInput: InputLine, candidateChain: CandidateChain): InputLine = ???
}
