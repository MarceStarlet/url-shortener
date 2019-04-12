package controllers

import javax.inject.{ Inject, Singleton }
import models.{ HttpShortUrl }
import play.api.Logger
import play.api.mvc.{ AbstractController, ControllerComponents }
import services.{ ShortURLGenerator => ShortURLGeneratorService }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ShortURLGenerator @Inject() (cc: ControllerComponents, shortURLGenerator: ShortURLGeneratorService)
  extends AbstractController(cc) {

  import models.JsonFormats._

  val logger = Logger(getClass)

  def generateShortURL() = Action.async(parse.json) { content =>
    logger.info(s"Content=>${content.body}")
    content.body.validate[HttpShortUrl].map {
      original =>
        shortURLGenerator.createShortURL(original.original).map {
          result => Created(result)
        }
    }.getOrElse(Future.successful(BadRequest("Invalid request")))
  }
}
