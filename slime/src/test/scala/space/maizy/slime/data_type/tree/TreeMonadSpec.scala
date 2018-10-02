package space.maizy.slime.data_type.tree

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.BaseSpec

class TreeMonadSpec extends BaseSpec {

  "TreeMonad" should "flatMap" in {
    import cats.syntax.flatMap._
    import cats.syntax.functor._
    import Tree._
    import TreeMonad._

    val res = for {
      a <- branch(leaf("A"), leaf("B"))
      b <- branch(leaf(a + "!"), leaf(a + "?"))
    } yield b

    res shouldBe branch(
      branch(leaf("A!"), leaf("A?")),
      branch(leaf("B!"), leaf("B?"))
    )
  }

  // TODO: tests for tailRecM, pure
}
