package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

/**
 * Cursor position may be at the end of the string:
 * Line chars:        [A]   [B]   [C]   [D]
 * Cursor postion:  0     1     2     3     4
 */
final case class InputLine(string: String, cursorPosition: Int) {
  val length: Int = string.length
  val cursorAtTheEnd: Boolean = cursorPosition == length

  require(cursorPosition <= length, "Cursor position must be less or equals line length")
}
