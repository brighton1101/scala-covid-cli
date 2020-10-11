package coviddata.client

trait HttpClient {

    /**
     * Perform get request to url, and return response body
     * as a string
     * @param url string - target url to fetch result from
     * @return string - result from successful http request
     */
    def get(url: String): Option[String]
}