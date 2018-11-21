package nl.dpes.cats_future_either

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object CatsFutureEither extends App {
  println(Await.result(new Service(Client).deduct, Duration.Inf))
}
