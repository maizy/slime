package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList

class InputSpec extends BaseSpec {
  "Input" should "returns selected arg" in {
    val input = Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" "),
        InputArg("arg2", Some(4))
      )
    )
    input.selectedArg shouldBe Some(InputArg("arg2", Some(4)))
    input.selected shouldBe Some(InputArg("arg2", Some(4)))
  }

  it should "returns selected div" in {
    val input = Input(
      NonEmptyList.of(
        InputArg("arg"),
        InputDivider(" ", Some(1)),
        InputArg("arg2")
      )
    )
    input.selectedDiv shouldBe Some(InputDivider(" ", Some(1)))
    input.selected shouldBe Some(InputDivider(" ", Some(1)))
  }
}
