package nl.dpes.domain

package object convertor {
  implicit def convert(from: Address[Id]): Address[Option] = Address[Option](
    Some(from.streetNameAndHouseNumber),
    Some(from.city),
    Some(from.zipCode)
  )

  implicit def convert(from: Person[Id]): Person[Option] = Person[Option](
    Some(from.firstName),
    Some(from.lastName),
    Some(from.emailAddres),
    Some(from.phoneNumber),
    Some(convert(from.address))
  )

  def checkValueAvailable[A](value: Option[A]): Option[ValidationError[A]] = value match {
    case None => Some(ValidationError("is mandatory"))
    case _ => None
  }

  implicit def convertOptionalAddress(from: Address[Option]): Either[Address[OptionalValidationError], Address[Id]] = from match {
    case Address(Some(streetNameAndHouseNumber), Some(city), Some(zipCode)) =>
      Right(Address[Id](streetNameAndHouseNumber, city, zipCode))
    case Address(streetNameAndHouseNumber, city, zipCode) =>
      Left(Address[OptionalValidationError](
        checkValueAvailable(streetNameAndHouseNumber),
        checkValueAvailable(city),
        checkValueAvailable(zipCode)
      ))
  }

  implicit def convertOptionalPerson(from: Person[Option]): Either[Person[OptionalValidationError], Person[Id]] = from match {
    case Person(Some(firstName), Some(lastName), Some(emailAddres), Some(phoneNumber), Some(address)) => convertOptionalAddress(address) match {
      case Right(address) => Right(Person[Id](firstName, lastName, emailAddres, phoneNumber, address))
      case Left(address) => Left(Person[OptionalValidationError](None, None, None, None, None))
    }

    case Person(firstName, lastName, emailAddres, phoneNumber, address) =>
      Left(Person[OptionalValidationError](
        checkValueAvailable(firstName),
        checkValueAvailable(lastName),
        checkValueAvailable(emailAddres),
        checkValueAvailable(phoneNumber),
        None
      ))
  }
}
