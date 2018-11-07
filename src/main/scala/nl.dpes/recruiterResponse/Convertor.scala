package nl.dpes.recruiterResponse

trait Convertor[C[_], F[_], T[_]] {
  def convert(from: C[F[_]]): C[T[_]]
}
object Convertor {
  implicit object AddressIdToOptionConvertor extends Convertor[Address, Id[_], Option[_]] {
    override def convert(from: Address[Id[_]]): Address[Option[_]] = Address[Option[_]](
      Some(from.streetNameAndHouseNumber),
      Some(from.city),
      Some(from.zipCode)
    )
  }
  implicit object RecruiterIdToOptionConvertor extends Convertor[Recruiter, Id[_], Option[_]] {
    override def convert(from: Recruiter[Id]): Recruiter[Option] = Recruiter[Option](
      Some(from.firstName),
      Some(from.lastName),
      Some(from.emailAddres),
      Some(from.phoneNumber),
      Some(AddressIdToOptionConvertor.convert(from.address))
    )
  }
}
