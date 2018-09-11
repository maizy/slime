package space.maizy.slime.test

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.{ Candidate, InputLine }
import space.maizy.slime.completer.{ Completer, RuleCompleter }

object TestSpec {

  // FIXME: temp
  val fakeCompleter: Completer = new RuleCompleter() {
    override def generateCandidates(currentInput: InputLine): List[Candidate] = {
      val tail = List("status", "--type")
      List(
        Candidate(tail, "A", complete = false),
        Candidate(tail :+ "B", "C"),
        Candidate(tail, "D", complete = false)
      )
    }

    override def selectCandidate(currentInput: InputLine, candidate: Candidate): InputLine =
      InputLine("completer result", 15)
  }
}
