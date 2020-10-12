package coviddata.view

import coviddata.model.CovidDataByLocation
import coviddata.model.AggregatedCovidData

/**
 * Base View 
 * @param {[type]} data:          Option[Seq[CovidDataByLocation]] [description]
 * @param {[type]} aggregateData: Option[AggregatedCovidData]       [description]
 */
class View(val date: String, val data: Option[Seq[CovidDataByLocation]], val aggregateData: Option[AggregatedCovidData]) {
    def show(): Unit = {}
}

abstract class ViewDecorator(decoratedView: View) 
    extends View(decoratedView.date, decoratedView.data, decoratedView.aggregateData) {
    override def show(): Unit = {
        decoratedView.show()
    }
}
