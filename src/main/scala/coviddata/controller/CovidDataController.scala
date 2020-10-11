package coviddata.controller

import coviddata.Csv
import coviddata.client.CovidDataClient
import coviddata.model.CovidDataByLocation

class CovidDataController(client: CovidDataClient) {

    // Store csv data keyed by 'date', in format 'mm-dd-yyyy'
    // avoids repeat lookups in case multiple operations needed.
    val csvByDate = scala.collection.mutable.Map[String, Csv]()

    /**
     * Fetches location data case classes based on date string provided.
     * @type {[type]}
     */
    def fetchLocationDataByDate(date: String): Option[Seq[CovidDataByLocation]] = {
        _fetchDataByDate(date)
            .map(csv => 
                csv.mapValsToObjs(CovidDataByLocation.fromCSSEGISandCsvRow))
    }

    /**
     * Fetches aggregated sum of datapoints across all rows for all numeric columns
     */

    /**
     * Attempt to get csv data from map. If not present, make
     * client request to fetch data from client.
     * @param date string - date in 'mm-dd-yyyy' format
     * @return Option[Csv] containing data for that date
     */
    private def _fetchDataByDate(date: String): Option[Csv] = {
        csvByDate.get(date) match {
            case None => 
                client
                    .dailyReportByDate(date)
                    .map(res => {
                        val csv = new Csv(res, dropHeader=true)
                        csvByDate.put(date, csv)
                        csv
                    })
            case Some(found: Csv) => Some(found)
        }
    }
}