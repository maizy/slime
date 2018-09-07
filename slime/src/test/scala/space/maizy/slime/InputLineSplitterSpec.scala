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

    val expected = Input(
      NonEmptyList.of(
        InputDivider(""),
        InputArg("cmd"),
        InputDivider("   "),
        InputArg("--opt"),
        InputDivider(" \n "),
        InputArg("opt2"),
        InputDivider("|"),
        InputArg(""),  // because "|" matches exactly one char
        InputDivider("|"),
        InputArg("opt3", Some(4))
      )
    )
    real shouldBe expected
  }

  it should "detect non empty divider at the begin of the line" in {
    fail("todo")
  }

  it should "detect cursor position" in {
    fail("todo")
  }

  it should "detect cursor position when it inside divider" in {
    fail("todo")
  }

  it should "detect cursor position when it inside argument" in {
    fail("todo")
  }

  it should "detect cursor position when it at the begin of the line starts with divider" in {
    fail("todo")
  }

  it should "detect cursor position when it at the begin of the line starts with argument" in {
    fail("todo")
  }

  it should "detect cursor position when it at the end of the line ends with divider" in {
    fail("todo")
  }

  it should "detect cursor position when it at the end of the line ends with argument" in {
    fail("todo")
  }

  it should "split empty line" in {
    fail("todo")
  }


}
