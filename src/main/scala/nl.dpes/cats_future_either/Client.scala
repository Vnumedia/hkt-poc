package nl.dpes.cats_future_either

import scala.concurrent.Future

import scalaz.\/

trait Client {
  def getCredits: Future[Either[String , Seq[String]]]
  def deductCredit(id: String): Future[Either[String, Unit]]
}
object Client extends Client {
  def getCredits: Future[Either[String, Seq[String]]] = Future.successful(Right(Seq("profit")))

  def deductCredit(id: String): Future[Either[String, Unit]] = Future.successful(Right(()))
}
