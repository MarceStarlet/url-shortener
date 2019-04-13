package controllers

import javax.inject.{ Inject, Singleton }
import models.HttpShortUrl
import play.api.Logger
import play.api.libs.json.Json
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

    logger.info(s"Generating short URL for: ${content.body}")

    content.body.validate[HttpShortUrl].map { original =>
      shortURLGenerator.createShortURL(original.original).map {
        result => Created(Json.obj("shortURL" -> formatShortUrl(content.host, result)))
      }
    }.getOrElse(Future.successful(BadRequest("Invalid request")))
  }

  def redirectToOriginal(shortURLId: String) = Action.async {
    logger.info(s"Redirecting from: ${shortURLId}")

    shortURLGenerator.resolveOriginalURL(shortURLId).map { result =>
      result match {
        case Some(str) => Ok(Json.obj("redirect" -> str))
        case None => NotFound
      }
    }
  }

  private def formatShortUrl(host: String, shortedUrlId: String): String = {
    s"http://${host}/${shortedUrlId}"
  }
}
