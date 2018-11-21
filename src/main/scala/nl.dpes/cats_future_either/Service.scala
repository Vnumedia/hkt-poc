package nl.dpes.cats_future_either

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import cats.data.EitherT

class Service(client: Client) {
  def deduct: Future[Either[String, String]] = {
    val result = for {
      credits <- EitherT(client.getCredits)
      result <- EitherT(credits.headOption.map(client.deductCredit) getOrElse Future.successful(Left("no credit found")))
    } yield {
      "credits deducted"
    }
    result.value.recover { case e => Left(e.getMessage) }
  }
}
