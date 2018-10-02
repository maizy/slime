package space.maizy.slime.candidate_generator_tree

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import space.maizy.slime.CandidateGenerator
import space.maizy.slime.data_type.tree.{ Branch, Leaf, Tree }

object CandidateGeneratorTree {
  type Type = Tree[CandidateGenerator]
  def branch(left: Type, right: Type): Type = Branch(left, right)
  def leaf(value: CandidateGenerator): Type = Leaf(value)
}
