package coviddata.model

case class AggregatedCovidData(
    country: String,
    updatedDateTime: String,
    confirmed: Double,
    deaths: Double,
    recovered: Double,
    active: Double,
    totalPersonsTested: Double,
    averageMortalityRate: Double,
    averageTestingRate: Double
)

object AggregatedCovidData {
    def fromCovidDataLocationSeq(
        locationSeq: Seq[CovidDataByLocation],
        country: Option[String] = None): Option[AggregatedCovidData] = {
        val filteredLocationSeq: Seq[CovidDataByLocation] = 
            country
                .map((c: String) =>
                    locationSeq.filter((l: CovidDataByLocation) => l.country == c))
                .getOrElse(locationSeq)
        val len = filteredLocationSeq.length
        len match {
            case 0 => None
            case _ => Some(_fromCovidDataLocationSeq(filteredLocationSeq, country))
        }
    }

    def _fromCovidDataLocationSeq(
        locationSeq: Seq[CovidDataByLocation],
        country: Option[String] = None): AggregatedCovidData = {
        // Filter results by country if specifie
        val outCountry = 
            if (locationSeq.forall(_.country == locationSeq.head.country))
                locationSeq(0).country
            else ""
        val outDate =
            if (locationSeq.forall(_.updatedDateTime == locationSeq.head.updatedDateTime))
                locationSeq(0).updatedDateTime
            else ""
        val aggrLocation: Seq[Double] = locationSeq
            .map(a => 
                Seq[Double](a.confirmed.getOrElse(0), a.deaths.getOrElse(0), a.recovered.getOrElse(0),
                    a.active.getOrElse(0), a.personsTested.getOrElse(0), a.mortalityRate.getOrElse(0),
                    a.testingRate.getOrElse(0)))
            .reduce((a: Seq[Double], b: Seq[Double]) => a.zipWithIndex.map {
                case (el: Double, index: Int) => el+b(index)
            })
        AggregatedCovidData(
            outCountry,
            outDate,
            aggrLocation(0),
            aggrLocation(1),
            aggrLocation(2),
            aggrLocation(3),
            aggrLocation(4),
            aggrLocation(5) / locationSeq.length,
            aggrLocation(6) / locationSeq.length
        )
    }
}