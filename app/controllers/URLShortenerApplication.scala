package controllers

import javax.inject.{ Inject, Singleton }
import play.api.Logger
import play.api.mvc.{ AbstractController, ControllerComponents }

@Singleton
class URLShortenerApplication @Inject() (cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  val logger = Logger(getClass)

  /**
   * Endpoint to the index web page
   * @return
   */
  def index() = Action {
    logger.info("Welcoming to URL Shortener index")
    Ok(views.html.index(""))
  }

}
