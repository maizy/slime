package space.maizy.slime.completer

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.{ Candidate, InputLine, InputArgs }

trait Completer {
  def splitLine(line: InputLine): InputArgs
  def generateCandidates(currentInput: InputLine): List[Candidate]
  def selectCandidate(currentInput: InputLine, candidate: Candidate): InputLine
}
