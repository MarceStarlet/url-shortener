package repositories

import models.{ ShortURL => ShortURLModel }
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait ShortURL {

  def save(entity: ShortURLModel): Future[WriteResult]

  def findByOriginal(original: String): Future[String]

  def findById(shorted: String): Future[String]

}
