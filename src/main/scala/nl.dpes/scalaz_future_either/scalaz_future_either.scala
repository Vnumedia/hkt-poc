package nl.dpes

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scalaz.Monad

package object scalaz_future_either {
  implicit val FutureMonad: Monad[Future] = new Monad[Future] {
    def point[A](a: => A): Future[A] = Future(a)
    def bind[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa flatMap f
  }
}
