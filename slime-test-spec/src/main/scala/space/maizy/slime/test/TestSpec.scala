package space.maizy.slime.test

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.completer.{ Completer, TreeCompleter }
import space.maizy.slime.{ Candidate, CandidateChain, EmptyTree, InputLine }

object TestSpec {

  // FIXME: temp
  val fakeCompleter: Completer = new TreeCompleter(EmptyTree) {
    override def generateCandidatesChains(currentInput: InputLine): List[CandidateChain] = {
      val chainOne = CandidateChain(
        CandidateChain.matchedValue("status"),
        CandidateChain.matchedValue("--type"),
        CandidateChain.candidates(
          Candidate("A", complete = false),
          Candidate("D")
        )
      )

      val chainTwo = CandidateChain(
        CandidateChain.matchedValue("status"),
        CandidateChain.matchedValue("--type"),
        CandidateChain.matchedValue("B"),
        CandidateChain.candidates(
          Candidate("C")
        )
      )

      val chainThree = CandidateChain(
        CandidateChain.matchedValue("status"),
        CandidateChain.matchedValue("--type"),
        CandidateChain.candidates(
          Candidate("X"),
          Candidate("Y"),
          Candidate("Z")
        ),
        CandidateChain.matchedValue("--subtype=")
      )
      List(chainOne, chainTwo, chainThree)
    }

    override def selectCandidateChain(currentInput: InputLine, candidateChain: CandidateChain): InputLine =
      InputLine("completer result", 15)
  }
}
