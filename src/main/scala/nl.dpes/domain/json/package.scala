package nl.dpes.domain

import spray.json.{JsNull, JsObject, JsonFormat, JsString, JsValue}
import nl.dpes.thrush._
import nl.dpes.domain.convertor._

package object json {
  /*
  case class Address[C[_]](streetNameAndHouseNumber: C[String], city: C[String], zipCode: C[String])

  case class Person[C[_]](firstName: C[String],
                          lastName: C[String],
                          emailAddres: C[String],
                          phoneNumber: C[String],
                          address: Address[C])
   */

  final case class JsonFormatter[A](format: A => JsonFormat[A])

  object JsonFormatter {
    implicit val addressIdFormatter: JsonFormatter[Address[Id]] = JsonFormatter(address =>
      new JsonFormat[Address[Id]] {
        override def write(obj: Address[Id]): JsValue = JsObject(
          "streetNameAndHouseNumber" -> JsString(obj.streetNameAndHouseNumber),
          "city"                     -> JsString(obj.city),
          "zipCode"                  -> JsString(obj.zipCode)
        )
        override def read(json: JsValue): Address[Id] = ???
    })

    implicit def optionalValidationErrorFormatter[A](validationError: OptionalValidationError[A]): JsValue = validationError match {
      case None => JsNull
      case Some(error) => JsString(error.error)
    }

    implicit val addressOptionalValidationErrorFormatter: JsonFormatter[Address[OptionalValidationError]] = JsonFormatter(address =>
      new JsonFormat[Address[OptionalValidationError]] {
        override def write(obj: Address[OptionalValidationError]): JsValue = {
          val c: OptionalValidationError[String] = obj.streetNameAndHouseNumber
          JsObject(
            "streetNameAndHouseNumber" -> obj.streetNameAndHouseNumber,
            "city"                     -> obj.city,
            "zipCode"                  -> obj.zipCode
          )
        }
        override def read(json: JsValue): Address[OptionalValidationError] = ???
    })
  }

  def toJson[A](value: A)(implicit formatter: JsonFormatter[A]): JsValue = formatter.format(value).write(value)
}

object Main extends App {
  import json._

  val address: Address[Id] = Address[Id]("mt.Lincolnweg", "Amsterdam", "1033SN")
  s"Address[Id] :: ${toJson(address)}" |> println

  val incompleteAddress = Address[Option](None, Some("Amsterdam"), None)
  val validatedAddress  = convertOptionalAddress(incompleteAddress).left.get
  s"Address[OptionalValidationError] :: ${toJson(validatedAddress)}" |> println
}
