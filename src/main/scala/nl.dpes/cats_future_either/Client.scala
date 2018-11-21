package nl.dpes.cats_future_either

import scala.concurrent.Future

import scalaz.\/

trait Client {
  def getCredits: Future[Either[String , Seq[String]]]
  def deductCredit(id: String): Future[Either[String, Unit]]
}
object Client extends Client {
//  def getCredits: Future[Either[String, Seq[String]]] = Future.successful(Right(Seq("profit")))
//  def getCredits: Future[Either[String, Seq[String]]] = Future.successful(\/-(Seq()))
//  def getCredits: Future[Either[String, Seq[String]]] = Future.successful(-\/("get credits error"))
  def getCredits: Future[Either[String, Seq[String]]] = Future.failed(new Exception("failed retrieving credits"))

  def deductCredit(id: String): Future[Either[String, Unit]] = Future.successful(Right(()))
//  def deductCredit(id: String): Future[Either[String, Unit]] = Future.successful(-\/("deduct credits error"))
//  def deductCredit(id: String): Future[Either[String, Unit]] = Future.failed(new Exception("failed deducting credits"))
}
