package coviddata.model

import scala.util.control.Exception.allCatch

/**
 * Generic case class representing covid data by location
 * Closely matches (but can be extended to other data sources):
 * @link https://github.com/CSSEGISandData/COVID-19/blob/master/csse_covid_19_data/csse_covid_19_daily_reports_us/10-09-2020.csv
 * @type CovidDataByLocation
 */
case class CovidDataByLocation(
    province: String,
    country: String,
    updatedDateTime: String,
    confirmed: Option[Double],
    deaths: Option[Double],
    recovered: Option[Double],
    active: Option[Double],
    personsTested: Option[Double],
    mortalityRate: Option[Double],
    testingRate: Option[Double]
)

object CovidDataByLocation {
    def fromCSSEGISandCsvRow(cols: Seq[String]): CovidDataByLocation = {
        val colsVal = (ind: Int) => if (ind >= cols.length) "" else cols(ind)
        CovidDataByLocation(
            cols(0),
            cols(1),
            cols(2),
            CovidDataByLocation._parseDouble(colsVal(5)),
            CovidDataByLocation._parseDouble(colsVal(6)),
            CovidDataByLocation._parseDouble(colsVal(7)),
            CovidDataByLocation._parseDouble(colsVal(8)),
            CovidDataByLocation._parseDouble(colsVal(11)),
            CovidDataByLocation._parseDouble(colsVal(13)),
            CovidDataByLocation._parseDouble(colsVal(16))
        )
    }

    def _isDoubleNumber(s: String): Boolean = (allCatch opt s.toDouble).isDefined

    def _parseDouble(inval: String): Option[Double] = {
        inval match {
            case valid if (CovidDataByLocation._isDoubleNumber(inval)) => Some(inval.toDouble)
            case _ => None
        }
    }
}