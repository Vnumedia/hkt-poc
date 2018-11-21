package nl.dpes.cats_future_either

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class ServiceSpec extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {
  val client: Client = mock[Client]

  before {
    reset(client)
  }

  behavior of "credit deduction"

  it should "deduct credits when available" in {
    when(client.getCredits) thenReturn Future.successful(Right(Seq("credit")))
    when(client.deductCredit(any[String])) thenReturn Future.successful(Right(()))

    Await.result(new Service(client).deduct, Duration.Inf) shouldBe Right("credits deducted")
  }

  it should "not deduct when no credits are available" in {
    when(client.getCredits) thenReturn Future.successful(Right(Seq()))

    Await.result(new Service(client).deduct, Duration.Inf) shouldBe Left("no credit found")

    verify(client, never).deductCredit(any[String])
  }

  behavior of "handling errors"

  it should "notify an error occurred retrieving credits" in {
    val error = "retreive credits error"
    when(client.getCredits) thenReturn Future.successful(Left(error))

    Await.result(new Service(client).deduct, Duration.Inf) shouldBe Left(error)

    verify(client, never).deductCredit(any[String])
  }

  it should "notify an error occurred deducting credits" in {
    val error = "deduct credits error"
    when(client.getCredits) thenReturn Future.successful(Right(Seq("credit")))
    when(client.deductCredit(any[String])) thenReturn Future.successful(Left(error))

    Await.result(new Service(client).deduct, Duration.Inf) shouldBe Left(error)
  }

  behavior of "failing services"

  it should "notify the service failed retreiving credits" in {
    val error = "service failed retrieving credits"
    when(client.getCredits) thenReturn Future.failed(new Exception(error))

    Await.result(new Service(client).deduct, Duration.Inf) shouldBe Left(error)

    verify(client, never).deductCredit(any[String])
  }

  it should "notify the service failed deducting credits" in {
    val error = "service failed deducting credits"
    when(client.getCredits) thenReturn Future.successful(Right(Seq("credit")))
    when(client.deductCredit(any[String])) thenReturn Future.failed(new Exception(error))

    Await.result(new Service(client).deduct, Duration.Inf) shouldBe Left(error)
  }
}
