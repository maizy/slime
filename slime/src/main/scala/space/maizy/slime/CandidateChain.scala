package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList

case class CandidateChain(elements: NonEmptyList[CandidateChain.Element])

object CandidateChain {
  type Element = Either[MatchedValue, List[Candidate]]

  def apply(element: Element, rest: Element*): CandidateChain =
    CandidateChain(NonEmptyList.one(element) ++ rest.toList)

  def matchedValue(value: String): Element = Left(MatchedValue(value))
  def candidates(candidates: List[Candidate]): Element = Right(candidates)
  def candidates(candidate: Candidate, rest: Candidate*): Element =
    candidates(candidate +: rest.toList)
}
