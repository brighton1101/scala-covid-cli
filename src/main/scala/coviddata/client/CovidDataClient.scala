package coviddata.client

import coviddata.model.CovidDataByLocation

trait CovidDataClient {

    /**
     * Given string representation of date, in 'mm-dd-yyyy' format
     * (as enforced by other parts of app), fetch string response
     * @param date string - date in 'mm-dd-yyyy' format
     * @return string - response body
     */
    def dailyReportByDate(date: String): Option[String]
}