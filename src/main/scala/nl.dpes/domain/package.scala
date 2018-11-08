package nl.dpes

package object domain {
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
                          address: Address[C])
}
