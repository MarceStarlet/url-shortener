package repositories

import javax.inject.Inject
import play.api.Logger
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection
import models.{ ShortURL => ShortURLModel }
import reactivemongo.bson.{ BSONDocument }
import reactivemongo.play.json._

import scala.concurrent.{ ExecutionContext, Future }

/**
 * The Repository implementation for MongoDB
 * @param ec
 * @param reactiveMongoApi
 */
class ShortURLMongo @Inject() (implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi)
  extends ShortURL {

  val DBCollection: String = "shorturl"

  import models.JsonFormats._

  private val logger = Logger(getClass)

  def shortUrlCollection: Future[JSONCollection] = {
    logger.debug(s"collection:${DBCollection}")
    reactiveMongoApi.database.map(_.collection(DBCollection))
  }

  def save(entity: ShortURLModel): Future[WriteResult] = {
    logger.info(s"Saving on ${DBCollection}")
    shortUrlCollection.flatMap { db =>
      db.insert(entity)
    }
  }

  def findByOriginalURL(original: String): Future[String] = {
    logger.info("Looking for original URL")
    shortUrlCollection.flatMap { db =>
      db.find(BSONDocument("original" -> original))
        .one[BSONDocument].flatMap { result =>
          val res: BSONDocument = result.getOrElse(BSONDocument("shorted" -> ""))
          logger.info(s"findByOriginal: ${res.getAs[String]("shorted").get}")
          Future(res.getAs[String]("shorted").get)
        }
    }
  }

  def findByShortId(shorted: String): Future[String] = {
    logger.info("Looking for short id")
    shortUrlCollection.flatMap { db =>
      db.find(BSONDocument("shorted" -> shorted))
        .one[BSONDocument].flatMap { result =>
          val res: BSONDocument = result.getOrElse(BSONDocument("original" -> ""))
          logger.info(s"findById: ${res.getAs[String]("original").get}")
          Future(res.getAs[String]("original").get)
        }
    }
  }
}
