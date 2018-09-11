package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

import cats.data.NonEmptyList

sealed trait InputPart {
  def value: String
  def cursorPosition: Option[Int]
  def selected: Boolean = cursorPosition.isDefined
}

final case class InputArg(value: String, cursorPosition: Option[Int] = None) extends InputPart

final case class InputDivider(value: String, cursorPosition: Option[Int] = None) extends InputPart

final case class Input(inputParts: NonEmptyList[InputPart]) {
  lazy val args: List[InputArg] = inputParts.collect{case x: InputArg => x}
  lazy val dividers: List[InputDivider] = inputParts.collect{case x: InputDivider => x}
  def selectedArg: Option[InputArg] = args.find(_.selected)
  def selectedDiv: Option[InputDivider] = dividers.find(_.selected)
  def selected: Option[InputPart] = inputParts.find(_.selected)
}
