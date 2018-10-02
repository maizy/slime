package space.maizy.slime.data_type.tree

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.Monad

object TreeMonad {
  import Tree._

  implicit val treeMonad: Monad[Tree] = new Monad[Tree] {

    override def pure[A](x: A): Tree[A] = leaf(x)

    override def flatMap[A, B](tree: Tree[A])(f: A => Tree[B]): Tree[B] =
      tree match {
        case Leaf(v) => f(v)
        case Branch(a, b) => Branch(
          flatMap(a)(f),
          flatMap(b)(f)
        )
      }

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
      f(a) match {
        case Leaf(Left(x)) => tailRecM(x)(f)
        case Leaf(Right(x)) => pure(x)
        case Branch(l, r) =>
          Branch(
            flatMap(l) {
              case Left(x) => tailRecM(x)(f)
              case Right(x) => pure(x)
            },
            flatMap(r) {
              case Left(x) => tailRecM(x)(f)
              case Right(x) => pure(x)
            }
          )
      }
  }
}

