package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

final case class InputArg(value: String, cursorPosition: Option[Int] = None) {
  val selected: Boolean = cursorPosition.isDefined
}

final case class InputArgs(args: List[InputArg]) {
  def selectedArg: Option[InputArg] = args.find(_.selected)
}
