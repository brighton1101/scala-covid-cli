package coviddata.client

import scalaj.http.Http

/**
 * Scalaj implementation of `HttpClient`
 */
class ScalajHttpClient extends HttpClient {
    /**
     * Perform get request to url, and return response body
     * as a string
     * @param url string - target url to fetch result from
     * @return string - result from successful http request
     */
    def get(url: String): Option[String] = {
        Http(url).asString match {
            case res if (res.isSuccess) => Some(res.body)
            case _ => None
        }
    }
}