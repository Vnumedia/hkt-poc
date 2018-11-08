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

  def checkValueAvailable[A](value: Option[A], fieldName: String): Option[ValidationError[A]] = value match {
    case None => Some(ValidationError(s"$fieldName is mandatory"))
    case _ => None
  }

  implicit def convertOptionalAddress(from: Address[Option]): Either[Unit, Address[Id]] = from match {
    case Address(Some(streetNameAndHouseNumber), Some(city), Some(zipCode)) =>
      Right(Address[Id](streetNameAndHouseNumber, city, zipCode))
    case Address(streetNameAndHouseNumber, city, zipCode) =>
      Left(())
  }

  implicit def convertOptionalPerson(from: Person[Option]): Either[Unit, Person[Id]] = from match {
    case Person(Some(firstName), Some(lastName), Some(emailAddres), Some(phoneNumber), Some(address)) => for {
      address <- convertOptionalAddress(address)
    } yield Person[Id](firstName, lastName, emailAddres, phoneNumber, address)
    case _ => Left(())
  }
}
