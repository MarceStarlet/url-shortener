package controllers

import javax.inject.{ Inject, Singleton }
import play.api.Logger
import play.api.mvc.{ AbstractController, ControllerComponents }

@Singleton
class URLShortenerApplication @Inject() (cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  val logger = Logger(getClass)

  def index = Action {
    logger.info("index welcoming")
    Ok(views.html.index("Hello World!"))
  }

}
