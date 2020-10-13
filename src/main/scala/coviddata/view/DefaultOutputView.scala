package coviddata.view

class DefaultOutputView(view: View) extends ViewDecorator(view) {
    override def show(): Unit = {
        println(s"""
            | COVID Data CLI
            | Date: ${date}""".stripMargin)
        super.show()
    }
}