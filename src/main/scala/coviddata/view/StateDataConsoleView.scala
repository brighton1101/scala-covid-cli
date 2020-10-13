package coviddata.view

class StateDataConsoleView(view: View) extends ViewDecorator(view) {
    override def show(): Unit = {
        println(s"""
        | State Data:
        | ${consoleStateDataView()}
        |""".stripMargin)
        super.show()
    }

    def consoleStateDataView(): String = {
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