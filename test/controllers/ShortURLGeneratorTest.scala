package controllers

import org.scalatestplus.play.PlaySpec
import services.{ShortURLGenerator => ShortURLGeneratorService}

import play.api.test.Helpers._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ShortURLGeneratorTest extends PlaySpec {

  ""

  "ShortURLGenerator shortURLController" should {
    "return a valid result with Action" in {

      val original = "wwww.google.com"


      val shortURLGeneratorServ: ShortURLGeneratorService = new ShortURLGeneratorService {
        def createShortURL(original: String): Future[Boolean] =  Future{true}
      }

      val shortURLcontroller = new ShortURLGenerator(stubControllerComponents(), shortURLGeneratorServ)

      val result = shortURLcontroller.generateShortURL()

      //contentAsString(result) must equal(true)
    }
  }

}
