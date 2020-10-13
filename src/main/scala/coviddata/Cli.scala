package coviddata

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import coviddata.client.ScalajHttpClient
import coviddata.client.CSSEGISandCovidClient
import coviddata.controller.CovidDataController
import coviddata.view.{View, DefaultOutputView, StateDataConsoleView, AggregateDataConsoleView, HtmlView}

object Cli {
  val usage =
    """
      | COVID DATA: By default, gets latest days' covid data from: https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data
      | Optional flags:
      | - -d: Specify a date in the format mm-dd-yyyy. Ex: '-d="10-09-2020"'
      |""".stripMargin

  def main(args: Array[String]): Unit = {
    val options = scala.collection.mutable.Map[String,String]();
    args.foreach(arg => arg match {
      case "-h" => {
        println(usage)
        System.exit(0)
      }
      case date if date.startsWith("-d=") =>
        options.put("date",
          _parseDateOpt(date).getOrElse(throw new Exception("Invalid date provided")))
      case "-a" =>
        options.put("showStateData", "true")
      case "-html" =>
        options.put("html", "true")
      case default => {
        println(s"Invalid option $default")
        System.exit(1)
      }
    })
    val date = options.getOrElse("date", _getCurrDate)
    val httpClient = new ScalajHttpClient
    val covidClient = new CSSEGISandCovidClient(httpClient)
    val controller = new CovidDataController(covidClient)
    val stdata = controller.fetchLocationDataByDate(date)
    val aggdata = controller.fetchAggregateDataByDate(date)
    val decoratedView = options
      .toSeq
      .foldLeft(new View(date, stdata, aggdata)) {
          (view, optpair) => optpair match {
            case ("showStateData", _) => new StateDataConsoleView(view)
            case ("html", _) => new HtmlView(view)
            case _ => view
          }
        }
    val view = new DefaultOutputView(new AggregateDataConsoleView(decoratedView))
    view.show
  }

  def _parseDateOpt(date: String): Option[String] = {
    date.replaceAll("\"", "").replaceAll("-d=", "") match {
      // This will match through 2022 - if there's still covid that's a massive bummer
      case valid if valid.matches("(0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])[-](202[0-2])") => Some(valid)
      case _ => None
    }
  }

  def _getCurrDate(): String = {
    LocalDate
      .now
      .format(DateTimeFormatter.ofPattern("MM-dd-YYYY"))
      .toString
  }
}
