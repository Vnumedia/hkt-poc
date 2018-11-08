package nl.dpes

import nl.dpes.recruiterResponse._
import nl.dpes.recruiterResponse.convertor._
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
  case class Person[C[_]](firstName: C[String],
                          lastName: C[String],
                          emailAddres: C[String],
                          phoneNumber: C[String],
                          address: C[Address[C]])

  val errors: Person[OptionalValidationError] = Person[OptionalValidationError](
    firstName = Some(ValidationError("te lang", "includes last name")),
    lastName = None,
    emailAddres = None,
    phoneNumber = None,
    address = None
  )

  val incompletePerson: Person[Option] = Person[Option](Some("first name"), None, None, None, None)

  val person: Person[Id] = Person[Id](
    "hoi",
    "daar",
    "foo@bar.baz",
    "0123456789",
    Address[Id]("mt.Lincolnweg", "Amsterdam", "1033SN")
  )

  val completePerson: Person[Option] = person
}

object Test extends App {
  def showResult(person: Either[Unit, Person[Id]]): Unit = println(person)

  incompletePerson |> convertOptionalPerson |> showResult

  completePerson |> convertOptionalPerson |> showResult
}
