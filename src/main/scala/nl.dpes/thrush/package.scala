package nl.dpes

package object thrush {
  implicit class Thrushed[A](val value: A) extends AnyVal {
    def |>[B](op: A => B): B = op(value)
  }
}
