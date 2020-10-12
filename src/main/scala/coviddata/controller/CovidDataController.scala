package coviddata.controller

import coviddata.Csv
import coviddata.client.CovidDataClient
import coviddata.model.CovidDataByLocation
import coviddata.model.AggregatedCovidData

class CovidDataController(client: CovidDataClient) {

    // Store csv data keyed by 'date', in format 'mm-dd-yyyy'
    // avoids repeat lookups in case multiple operations needed.
    val locationDataByDate = scala.collection.mutable.Map[String, Seq[CovidDataByLocation]]()

    /**
     * Fetches location data case classes based on date string provided.
     * @type {[type]}
     */
    def fetchLocationDataByDate(date: String): Option[Seq[CovidDataByLocation]] = {
        _fetchDataByDate(date)
    }

    /**
     * Fetch aggregate data by date
     */
    def fetchAggregateDataByDate(date: String): Option[AggregatedCovidData] = {
        _fetchDataByDate(date)
            .flatMap(res => AggregatedCovidData.fromCovidDataLocationSeq(res))
    }

    /**
     * Attempt to get csv data from map. If not present, make
     * client request to fetch data from client.
     * @param date string - date in 'mm-dd-yyyy' format
     * @return Option[Csv] containing data for that date
     */
    private def _fetchDataByDate(date: String): Option[Seq[CovidDataByLocation]] = {
        locationDataByDate.get(date) match {
            case None => 
                client
                    .dailyReportByDate(date)
                    .map(res => new Csv(res, dropHeader=true))
                    .map(csv => {
                        val ld = csv.mapValsToObjs(CovidDataByLocation.fromCSSEGISandCsvRow)
                        locationDataByDate.put(date, ld)
                        ld
                    })
                    
            case Some(found: Seq[CovidDataByLocation]) => Some(found)
        }
    }
}