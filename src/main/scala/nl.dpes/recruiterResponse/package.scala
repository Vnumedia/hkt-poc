package nl.dpes

import nl.dpes.recruiterResponse
import nl.dpes.recruiterResponse.Convertor.RecruiterIdToOptionConvertor
import nl.dpes.thrush._

package object recruiterResponse {
  sealed trait Result
  case object Empty extends Result
  case class Success(message: String) extends Result
  case class Failure(error: Error)

  case class UpdateRecruiterResponse()

  type Id[T] = T

  case class ValidationError[T](error: String*)
  type OptionalValidationError[T] = Option[ValidationError[T]]

  case class Address[C[_]](streetNameAndHouseNumber: C[String], city: C[String], zipCode: C[String])
  case class Recruiter[C[_]](firstName: C[String],
                             lastName: C[String],
                             emailAddres: C[String],
                             phoneNumber: C[String],
                             address: C[Address[C]])

  val errors: Recruiter[OptionalValidationError] = Recruiter[OptionalValidationError](
    firstName = Some(ValidationError("te lang", "includes last name")),
    lastName = None,
    emailAddres = None,
    phoneNumber = None,
    address = None
  )

  val incompleteRecruiter: Recruiter[Option] = Recruiter[Option](Some("first name"), None, None, None, None)

  val recruiter: Recruiter[Id] = Recruiter[Id](
    "hoi",
    "daar",
    "foo@bar.baz",
    "0123456789",
    Address[Id]("mt.Lincolnweg", "Amsterdam", "1033SN")
  )


  def checkValueAvailable[A](value: Option[A], fieldName: String): Option[ValidationError[A]] = value match {
    case None => Some(ValidationError(s"$fieldName is mandatory"))
    case _ => None
  }

  //  def toId(recruiter: Recruiter[Option]): Either[Recruiter[OptionalValidationError], Recruiter[Id]] = recruiter match {
  //    case Recruiter(Some(firstName), Some(lastName), Some(emailAddres), Some(phoneNumber), Some(address)) =>
  //      Right(Recruiter[Id](firstName, lastName, emailAddres, phoneNumber, address))
  //    case Recruiter(firstName, lastName, emailAddres, phoneNumber, address) =>
  //      Left(Recruiter[OptionalValidationError](
  //        checkValueAvailable(firstName, "firstName"),
  //        checkValueAvailable(lastName, "lastName"),
  //        checkValueAvailable(emailAddres, "emailAddres"),
  //        checkValueAvailable(phoneNumber, "phoneNumber"),
  //        checkValueAvailable(address, "address")
  //      ))
  //  }

  //  val validated: Either[Recruiter[OptionalValidationError], Recruiter[Id]] = toId(incompleteRecruiter)

//  def idToOption[A[_], B](from: A[Id[B]])(implicit convertor: Convertor[A, Id, Option]): A[Option[B]] =
//    implicitly[Convertor[A, Id, Option]].convert(from)


  val completeRecruiter: Recruiter[Option] = RecruiterIdToOptionConvertor.convert(recruiter) //idToOption(recruiter)
}

object Test extends App {
//  def showResult(recruiter: Either[Recruiter[OptionalValidationError], Recruiter[Id]]): Unit = recruiter match {
//    case Left(recruiterResponse.Recruiter(firstName, lastName, emailAddres, phoneNumber, address)) =>
//      Seq("LEFT", firstName, lastName, emailAddres, phoneNumber, address) foreach println
//
//    case Right(recruiterResponse.Recruiter(firstName, lastName, emailAddres, phoneNumber, address)) =>
//      Seq("RIGHT", firstName, lastName, emailAddres, phoneNumber, address) foreach println
//  }
//
//  incompleteRecruiter |> toId |> showResult
//
//  completeRecruiter |> toId |> showResult

  println(recruiterResponse.completeRecruiter)
}
