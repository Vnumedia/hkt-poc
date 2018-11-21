package nl.dpes.scalaz_future_either

import scala.concurrent.Future

import scalaz.\/

trait Client {
  def getCredits: Future[String \/ Seq[String]]
  def deductCredit(id: String): Future[String \/ Unit]
}
object Client extends Client {
//  def getCredits: Future[String \/ Seq[String]] = Future.successful(\/-(Seq("profit")))
//  def getCredits: Future[String \/ Seq[String]] = Future.successful(\/-(Seq()))
//  def getCredits: Future[String \/ Seq[String]] = Future.successful(-\/("get credits error"))
  def getCredits: Future[String \/ Seq[String]] = Future.failed(new Exception("failed retrieving credits"))

//  def deductCredit(id: String): Future[String \/ Unit] = Future.successful(\/-(()))
//  def deductCredit(id: String): Future[String \/ Unit] = Future.successful(-\/("deduct credits error"))
  def deductCredit(id: String): Future[String \/ Unit] = Future.failed(new Exception("failed deducting credits"))
}
