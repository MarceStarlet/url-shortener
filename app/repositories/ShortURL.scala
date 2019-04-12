package repositories

import models.{ ShortURL => ShortURLModel }
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait ShortURL {

  def save(entity: ShortURLModel): Future[WriteResult]

  def exists(original: String): Boolean

  def findById(shorted: String): Future[String]

}
