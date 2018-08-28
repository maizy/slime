package space.maizy.slime

import cats.data.NonEmptyList

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

class InputLineSplitterSpec extends BaseSpec {

  "InputLineSplitter" should "split line args" in {
    val testString = "cmd   --opt \n opt2||opt3"
    val inputLine = InputLine(testString, testString.length)
    inputLine.cursorAtTheEnd shouldBe true
    val splitters = NonEmptyList.of(
      "\\s+".r,
      "\\|".r
    )
    val real = InputLineSplitter.split(inputLine, splitters)

    val expected = InputArgs(
      List(
        InputArg("cmd"),
        InputArg("--opt"),
        InputArg("opt2"),
        InputArg(""),  // because "|" matches exactly one char
        InputArg("opt3")
      )
    )
    real shouldBe expected
  }

  it should "detect cursor position" in {
    fail("todo")
  }

  it should "detect cursor position when it inside divider" in {
    fail("todo")
  }

  it should "detect cursor position when it at the begin of the line" in {
    fail("todo")
  }

  it should "detect cursor position when it at the end of the line" in {
    fail("todo")
  }

  it should "detect cursor position when it at the begin of the line preceded by divider" in {
    fail("todo")
  }

  it should "detect cursor position when it at the end of the line followed by divider" in {
    fail("todo")
  }

}
