package space.maizy.slime.data_type.tree

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList
import space.maizy.slime.candidate_generator_tree.CandidateGeneratorTree
import space.maizy.slime.contrib.candidate_generator.FixedList
import space.maizy.slime.{ BaseSpec, CandidateGenerator }

class TreeFunctorSpec extends BaseSpec {

  import CandidateGeneratorTree._
  private val generator = FixedList("A", "B", "C")

  private def doubleVal(gen: CandidateGenerator): FixedList = {
    val generatedVals = gen.generate().map(_.value)
    FixedList(NonEmptyList.fromList(generatedVals.map(_ * 2)).getOrElse(NonEmptyList.one("oops")))
  }

  "TreeFunctor" should "works for leaf" in {
    import space.maizy.slime.data_type.tree.TreeFunctor._
    treeFunctor.map(leaf(generator))(doubleVal) shouldBe leaf(FixedList("AA", "BB", "CC"))

  }

  it should "works for tree" in {
    import space.maizy.slime.data_type.tree.TreeFunctor._
    val t: Tree[CandidateGenerator] = branch(
      leaf(FixedList("A")),
      branch(leaf(FixedList("B", "C")), leaf(FixedList("D")))
    )

    treeFunctor.map(t)(doubleVal) shouldBe branch(
      leaf(FixedList("AA")),
      branch(leaf(FixedList("BB", "CC")), leaf(FixedList("DD")))
    )
  }

  it should "works for empty tree" in {

  }
}
