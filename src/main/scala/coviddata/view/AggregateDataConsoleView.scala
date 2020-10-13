package coviddata.view

class AggregateDataConsoleView(view: View) extends ViewDecorator(view) {
    override def show(): Unit = {
        println(s"""
        | Aggregate Data:
        | ${consoleAggregateView()}""".stripMargin)
        super.show()
    }

    def consoleAggregateView(): String = {
        aggregateData match {
            case Some(d) => s"""Confirmed Cases: ${d.confirmed}
                | Deaths:          ${d.deaths}
                | Recovered:       ${d.recovered}
                | Active:          ${d.active}
                | Persons Tested:  ${d.totalPersonsTested}
                |""".stripMargin
            case _ => s"""
                | No aggregate data available.
                """.stripMargin
        }
    }
}