package space.maizy.slime.completer

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.{ CandidateChain, Input, InputLine }

trait Completer {
  def splitLine(line: InputLine): Input
  def generateCandidatesChains(currentInput: InputLine): List[CandidateChain]
  def selectCandidateChain(currentInput: InputLine, candidateChain: CandidateChain): InputLine
}
