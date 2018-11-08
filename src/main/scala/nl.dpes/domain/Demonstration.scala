package nl.dpes.domain

import nl.dpes.domain.convertor._
import nl.dpes.domain.ApiProtocol._
import nl.dpes.thrush._

import spray.json._

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

  incompletePerson |> convertOptionalPerson |> println
  convertOptionalPerson(incompletePerson).toJson |> println

  println("-"*30)

  completePerson |> convertOptionalPerson |> println
  convertOptionalPerson(completePerson).toJson |> println
}
