package space.maizy.slime.data_type.tree

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.Functor

object TreeFunctor {
  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](node: Tree[A])(f: A => B): Tree[B] = node match {
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
      case Leaf(v) => Leaf(f(v))
    }
  }
}
