package coviddata.model

case class AggregatedCovidData(
    country: String,
    updatedDateTime: String,
    confirmed: Option[Double],
    deaths: Option[Double],
    recovered: Option[Double],
    active: Option[Double],
    totalPersonsTested: Option[Double],
    averageMortalityRate: Option[Double],
    averageTestingRate: Option[Double]
)

object AggregatedCovidData {
    def fromCovidDataLocationSeq(
        locationSeq: Seq[CovidDataByLocation],
        country: Option[String] = None): Option[AggregatedCovidData] = {
        if (locationSeq.length < 1) None
        else AggregatedCovidData._fromCovidLocationSeq(locationSeq, country)
    }

    def _fromCovidLocationSeq(
        locationSeq: Seq[CovidDataByLocation],
        country: Option[String] = None): AggregatedCovidData = {
        // Filter results by country if specified
        val filteredLocationSeq = 
            country
                .flatMap(c => locationSeq.filter(l => l.country == c))
                .getOrElse(locationSeq)
        val (outCountry, outDate) = filteredLocationSeq.reduce((a, b) => a.country == b.country) match {
            case true => (filteredLocationSeq(0).country, filteredLocationSeq(0).updatedDateTime)
            case false => ("MIXED", "MIXED")
        }
        val aggrLocation = filteredLocationSeq
            .map(a => 
                Seq(a.confirmed.getOrElse(0), a.deaths.getOrElse(0), a.recovered.getOrElse(0),
                    a.active.getOrElse(0), a.personsTested.getOrElse(0), a.mortalityRate.getOrElse(0), a.testingRate.getOrElse(0)))
            .reduce((a, b) => a.zipWithIndex.map {
                case (el, index) => el+b(ind)
            })
        AggregatedCovidData(
            outCountry,
            outDate,
            
        )
    }
}