package nl.dpes

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import cats.Monad

package object cats_future_either {
  implicit val FutureMonad: Monad[Future] = new Monad[Future] {

    def bind[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa flatMap f

    override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa flatMap f

    override def tailRecM[A, B](a: A)(f: A => Future[Either[A, B]]): Future[B] = ???

    override def pure[A](x: A): Future[A] = Future(x)
  }
}
