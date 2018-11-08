package nl.dpes.domain
import nl.dpes.thrush._

import nl.dpes.domain.convertor._

object Demonstration extends App {
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

  Address[Option](None, Some("Amsterdam"), None) |> convertOptionalAddress |> println

  incompletePerson |> convertOptionalPerson |> println

  completePerson |> convertOptionalPerson |> println
}
