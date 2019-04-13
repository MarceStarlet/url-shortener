package repositories

import models.{ ShortURL => ShortURLModel }
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

/**
 * The ShortURL repository to persist data
 */
trait ShortURL {

  /**
   * Saves the ShortURL model entity
   * @param entity
   * @return
   */
  def save(entity: ShortURLModel): Future[WriteResult]

  /**
   * Looks by the original URL
   * @param original
   * @return
   */
  def findByOriginalURL(original: String): Future[String]

  /**
   * Looks by the short id
   * @param shorted
   * @return
   */
  def findByShortId(shorted: String): Future[String]

}
