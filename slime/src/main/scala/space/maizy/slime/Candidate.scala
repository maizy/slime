package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

/**
 * @param value value to insert, should not contain ANSI sequences
 * @param complete if selected value is a complete, separator will be added after it
 * @param description optional description, may contains ANSI sequences
 * @param _displayedValue optional displayed value, may contains ANSI sequences
 */
final case class Candidate(
    value: String,
    _displayedValue: Option[String] = None,
    complete: Boolean = true,
    description: Option[String] = None
) {
  val displayedValue: String = _displayedValue.getOrElse(value)
}

