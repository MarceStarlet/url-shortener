package services

import javax.inject.{ Inject, Singleton }
import models.ShortURL
import play.api.Logger
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.{ BSON, BSONObjectID }
import repositories.ShortURLMongo

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Success

trait ShortURLGenerator {

  def createShortURL(original: String): Future[String]

  def retrieveHttpUrl(shorted: String): Future[String]

}

@Singleton
class ShortURLGeneratorServ @Inject() (implicit ec: ExecutionContext, shortURLMongo: ShortURLMongo)
  extends ShortURLGenerator {

  private val logger = Logger(getClass)

  override def createShortURL(original: String): Future[String] = {
    logger.info("Creating new short URL")

    shortURLMongo.findByOriginal(original).flatMap { shorted =>
      if (shorted.isEmpty) {
        val mongoId = getMongoId()

        val shourtURLId = generateShortId(mongoId)

        val shortURL = ShortURL(Some(mongoId), original, shourtURLId, System.currentTimeMillis(),
          System.currentTimeMillis())

        val result: Future[WriteResult] = shortURLMongo.save(shortURL)

        Future(shourtURLId)
      } else {
        Future(shorted)
      }
    }
  }

  override def retrieveHttpUrl(shorted: String): Future[String] = ???

  private def getMongoId(): BSONObjectID = {
    BSONObjectID.generate()
  }

  private def generateShortId(mongoId: BSONObjectID): String = {
    mongoId.stringify.takeRight(6)
  }

}
