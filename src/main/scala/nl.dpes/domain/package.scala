package nl.dpes

import spray.json.{DefaultJsonProtocol, RootJsonFormat}


package object domain {
  sealed trait Result
  case object Empty extends Result
  case class Success(message: String) extends Result
  case class Failure(error: Error)

  case class UpdateRecruiterResponse()

  type Id[T] = T

  case class ValidationError[T](error: List[String])
  type OptionalValidationError[T] = Option[ValidationError[T]]

  case class Address[C[_]](streetNameAndHouseNumber: C[String], city: C[String], zipCode: C[String])

  case class Person[C[_]](firstName: C[String],
                          lastName: C[String],
                          emailAddres: C[String],
                          phoneNumber: C[String],
                          address: Address[C])

  object ApiProtocol extends DefaultJsonProtocol {
    implicit def validationErrorFormat[T]: RootJsonFormat[ValidationError[T]] = jsonFormat1(ValidationError[T])

    implicit lazy val addressIdFormat: RootJsonFormat[Address[Id]] = jsonFormat3(Address[Id])
    implicit lazy val addressOptionalValidationErrorFormat: RootJsonFormat[Address[OptionalValidationError]] = jsonFormat3(Address[OptionalValidationError])

    implicit lazy val personIdFormat: RootJsonFormat[Person[Id]] = jsonFormat5(Person[Id])
    implicit lazy val personOptionalValidationErrorFormat: RootJsonFormat[Person[OptionalValidationError]] = jsonFormat5(Person[OptionalValidationError])
  }
}
