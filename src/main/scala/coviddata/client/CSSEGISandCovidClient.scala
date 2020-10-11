package coviddata.client

/**
 * Client class to fetch data from the COVID-19 Data Repository
 * by the Center for Systems Science and Engineering (CSSE) at
 * Johns Hopkins University
 * See below for src:
 * @link https://github.com/CSSEGISandData/COVID-19
 * @constructor
 * @param client HttpClient - HttpClient implementation
 */
class CSSEGISandCovidClient(client: HttpClient) extends CovidDataClient {

    private val target = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/"

    /**
     * Given string representation of date, in 'mm-dd-yyyy' format
     * (as enforced by other parts of app), fetch string response
     * @param date string - date in 'mm-dd-yyyy' format
     * @return string - response body
     */
    def dailyReportByDate(date: String): Option[String] = {
        val url =
            s"${target}csse_covid_19_data/csse_covid_19_daily_reports_us/${date}.csv"
        client.get(url)
    }
}