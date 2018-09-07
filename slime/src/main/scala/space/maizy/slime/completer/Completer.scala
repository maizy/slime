package space.maizy.slime.completer

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.{ Candidate, InputLine, Input }

trait Completer {
  def splitLine(line: InputLine): Input
  def generateCandidates(currentInput: InputLine): List[Candidate]
  def selectCandidate(currentInput: InputLine, candidate: Candidate): InputLine
}
