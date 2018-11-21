package nl.dpes.scalaz_future_either

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

import scalaz.-\/

object ScalazFutureEither extends App {
  println(
    Try {
      Await.result(Service.deduct, Duration.Inf)
    } recover {
      case e => -\/(e.getMessage)
    } get
  )
}
