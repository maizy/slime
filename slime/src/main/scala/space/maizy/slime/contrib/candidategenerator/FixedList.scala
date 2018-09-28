package space.maizy.slime.contrib.candidategenerator

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList
import cats.implicits._
import space.maizy.slime.{ Candidate, CandidateGenerator, InputArg }

case class FixedList(vals: NonEmptyList[String]) extends CandidateGenerator {
  override def isMatched(arg: InputArg): Boolean = vals.contains_(arg.value)
  override def generate(): List[Candidate] = vals.toList.map(Candidate(_))
}
