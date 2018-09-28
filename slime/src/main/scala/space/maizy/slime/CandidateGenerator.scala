package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

trait CandidateGenerator {
  def isMatched(arg: InputArg): Boolean
  def generate(): List[Candidate]
}
