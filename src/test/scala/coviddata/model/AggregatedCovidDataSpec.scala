package coviddata.model

import org.scalatest._
import flatspec._
import matchers._

class AggregatedCovidDataSpec extends AnyFlatSpec with should.Matchers {
    val testSeq: Seq[CovidDataByLocation] = Seq(
        CovidDataByLocation(
            "California",
            "US",
            "random",
            Some(1),
            Some(1),
            Some(1),
            Some(1),
            Some(1),
            Some(1),
            Some(1)
        ),
        CovidDataByLocation(
            "Alabama",
            "NOT US",
            "random",
            Some(2),
            Some(2),
            Some(2),
            Some(2),
            Some(2),
            Some(2),
            Some(2)
        )
    )

    it should "sum values for all" in {
        val res = AggregatedCovidData.fromCovidDataLocationSeq(testSeq).get
        res.averageMortalityRate shouldBe 1.5
        res.averageTestingRate shouldBe 1.5
        res.totalPersonsTested shouldBe 3
        res.active shouldBe 3
        res.recovered shouldBe 3
        res.deaths shouldBe 3
        res.confirmed shouldBe 3
        res.country shouldBe ""
        res.updatedDateTime shouldBe "random"
    }

    it should "sum values given specified country" in {
        val res = AggregatedCovidData.fromCovidDataLocationSeq(testSeq, Some("US")).get
        res.averageMortalityRate shouldBe 1
        res.averageTestingRate shouldBe 1
        res.totalPersonsTested shouldBe 1
        res.active shouldBe 1
        res.recovered shouldBe 1
        res.deaths shouldBe 1
        res.confirmed shouldBe 1
        res.country shouldBe "US"
        res.updatedDateTime shouldBe "random"
    }

    it should "return none for empty seq" in {
        val res = AggregatedCovidData.fromCovidDataLocationSeq(Seq(), Some("US"))
        res shouldBe None
    }
}