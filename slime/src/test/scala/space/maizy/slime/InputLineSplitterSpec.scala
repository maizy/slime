package space.maizy.slime

import cats.data.NonEmptyList

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

class InputLineSplitterSpec extends BaseSpec {

  private val whitespaceSplitter = NonEmptyList.one("\\s+".r)

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
    InputLineSplitter.split(InputLine("  arg arg2", 1), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputDivider("  ", Some(1)),
        InputArg("arg"),
        InputDivider(" "),
        InputArg("arg2")
      )
    )
  }

  it should "detect cursor position" in {
    InputLineSplitter.split(InputLine("arg arg2", 6), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" "),
        InputArg("arg2", Some(2))
      )
    )
  }

  it should "detect cursor position when it inside divider" in {
    InputLineSplitter.split(InputLine("arg  arg2", 4), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider("  ", Some(1)),
        InputArg("arg2")
      )
    )

    InputLineSplitter.split(InputLine("arg arg2", 4), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" ", Some(1)),
        InputArg("arg2")
      )
    )
  }

  it should "detect cursor position when it inside argument" in {
    InputLineSplitter.split(InputLine("arg arg2", 5), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" "),
        InputArg("arg2", Some(1))
      )
    )

    InputLineSplitter.split(InputLine("arg arg2", 3), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg", Some(3)),
        InputDivider(" "),
        InputArg("arg2")
      )
    )
  }

  it should "detect cursor position when it at the begin of the line starts with divider" in {
    InputLineSplitter.split(InputLine(" arg", 0), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputDivider(" ", Some(0)),
        InputArg("arg")
      )
    )
  }

  it should "detect cursor position when it at the begin of the line starts with argument" in {
    InputLineSplitter.split(InputLine("arg arg2", 0), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg", Some(0)),
        InputDivider(" "),
        InputArg("arg2")
      )
    )
  }

  it should "detect cursor position when it at the end of the line ends with divider" in {
    InputLineSplitter.split(InputLine("arg arg2   ", 10), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" "),
        InputArg("arg2"),
        InputDivider("   ", Some(2))
      )
    )
  }

  it should "detect cursor position when it at the end of the line ends with argument" in {
    InputLineSplitter.split(InputLine("arg arg2", 8), whitespaceSplitter) shouldBe Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" "),
        InputArg("arg2", Some(4))
      )
    )
  }

  it should "split empty line" in {
    InputLineSplitter.split(InputLine("", 0), whitespaceSplitter) shouldBe Input(
      NonEmptyList.one(
        InputDivider("", Some(0))
      )
    )
  }

  it should "split line with one argument" in {
    InputLineSplitter.split(InputLine("arg", 3), whitespaceSplitter) shouldBe Input(
      NonEmptyList.one(
        InputArg("arg", Some(3))
      )
    )

    InputLineSplitter.split(InputLine("arg", 0), whitespaceSplitter) shouldBe Input(
      NonEmptyList.one(
        InputArg("arg", Some(0))
      )
    )

    InputLineSplitter.split(InputLine("arg", 1), whitespaceSplitter) shouldBe Input(
      NonEmptyList.one(
        InputArg("arg", Some(1))
      )
    )
  }

}
