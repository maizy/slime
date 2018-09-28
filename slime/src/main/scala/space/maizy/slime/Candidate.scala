package space.maizy.slime

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2018
 * See LICENSE.txt for details.
 */

/**
 * @param value values to insert, should not contain ANSI sequences
 * @param complete if selected value is a complete, separator will be added after it
 * @param description optional description, may contains ANSI sequences
 * @param _displayedValue optional displayed value, may contains ANSI sequences
 *
 * TODO: how to encode step skipping?
 * TODO: how to link matched rule with its value? Are we need it?
 */
final case class Candidate(
    value: String,
    _displayedValue: Option[String] = None,
    complete: Boolean = true,
    description: Option[String] = None
) {
  val displayedValue: String = _displayedValue.getOrElse(value)
}

object Candidate {
  def apply(value: String): Candidate = new Candidate(value)
}
