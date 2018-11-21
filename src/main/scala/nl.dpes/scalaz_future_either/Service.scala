package nl.dpes.scalaz_future_either

import scala.concurrent.Future

import scalaz.{-\/, EitherT, \/}

object Service {
  def deduct: Future[String \/ String] = {
    val result = for {
      credits <- EitherT(Client.getCredits)
      result <- EitherT(credits.headOption.map(Client.deductCredit) getOrElse Future.successful(-\/("No credit found")))
    } yield {
      "credits deducted"
    }
    result.run
  }
}
