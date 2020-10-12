package coviddata.view

class ConsoleView(view: View, showStateData: Boolean = false) extends ViewDecorator(view) {
    override def show(): Unit = {
        println(s"""
        | COVID US Data Output
        | Date: ${view.date}
        |
        | Aggregate Data:
        | ${consoleAggregateView()}
        |
        | State Data:
        | ${consoleStateDataView()}
        |""".stripMargin)
        super.show()
    }

    def consoleAggregateView(): String = {
        view.aggregateData match {
            case Some(d) => s"""
                | Confirmed Cases: ${d.confirmed}
                | Deaths:          ${d.deaths}
                | Recovered:       ${d.recovered}
                | Active:          ${d.active}
                | Persons Tested:  ${d.totalPersonsTested}
                |""".stripMargin
            case _ => s"""
                | No aggregate data available
                """.stripMargin
        }
    }

    def consoleStateDataView(): String = {
        showStateData match {
            case true => _consoleStateDataView()
            case false => "Rerun with -a flag to show full state data output"
        }
    }

    def _consoleStateDataView(): String = {
        val notApp = "Not Found"
        data match {
            case Some(d) => d.foldLeft("") {
                (curr, sd) => curr + s"""
                | State:           ${sd.province}
                | Confirmed Cases: ${sd.confirmed.getOrElse(notApp)}
                | Deaths:          ${sd.deaths.getOrElse(notApp)}
                | Recovered:       ${sd.recovered.getOrElse(notApp)}
                | Active:          ${sd.active.getOrElse(notApp)}
                | Persons Tested:  ${sd.personsTested.getOrElse(notApp)}
                | Mortality Rate:  ${sd.mortalityRate.getOrElse(notApp)}
                | Testing Rate:    ${sd.testingRate.getOrElse(notApp)}
                |""".stripMargin
            }
            case _ => "No state data available."
        }
    }
}