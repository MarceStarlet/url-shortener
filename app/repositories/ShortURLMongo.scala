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

class ShortURLMongo @Inject() (implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi)
  extends ShortURL {

  import models.JsonFormats._

  private val logger = Logger(getClass)

  def shortUrlCollection: Future[JSONCollection] = {
    reactiveMongoApi.database.map(_.collection("shorturl"))
  }

  def save(entity: ShortURLModel): Future[WriteResult] = {
    shortUrlCollection.flatMap { db =>
      db.insert(entity)
    }
  }

  def findByOriginal(original: String): Future[String] = {
    shortUrlCollection.flatMap { db =>
      db.find(BSONDocument("original" -> original))
        .one[BSONDocument].flatMap { result =>
          val res: BSONDocument = result.getOrElse(BSONDocument("shorted" -> ""))
          logger.info(s"findByOriginal: ${res.getAs[String]("shorted").get}")
          Future(res.getAs[String]("shorted").get)
        }
    }
  }

  def findById(shorted: String): Future[String] = {
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
