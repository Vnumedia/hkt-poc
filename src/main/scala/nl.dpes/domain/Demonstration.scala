package nl.dpes.domain

import nl.dpes.domain.convertor._
import nl.dpes.thrush._

object Demonstration extends App {
  val incompleteAddress = Address[Option](None, Some("Amsterdam"), None)

  val incompletePerson: Person[Option] = Person[Option](Some("first name"), None, None, None, incompleteAddress)

  val person: Person[Id] = Person[Id](
    "hoi",
    "daar",
    "foo@bar.baz",
    "0123456789",
    Address[Id]("mt.Lincolnweg", "Amsterdam", "1033SN")
  )

  val completePerson: Person[Option] = person

//  private def toJson(value: Either[Address[OptionalValidationError], Address[Id]]) =
//    value.toJson

//  incompleteAddress |> convertOptionalAddress |> toJson

  incompletePerson |> convertOptionalPerson |> println

  completePerson |> convertOptionalPerson |> println
}
