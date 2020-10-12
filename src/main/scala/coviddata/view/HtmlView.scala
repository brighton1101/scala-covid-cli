package coviddata.view

class HtmlView(view: View) extends ViewDecorator(view) {
    override def show(): Unit = {
        println("in html")
        super.show()
    }
}