package repositories

import javax.inject.Inject
import play.api.Logger
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection
import models.{ ShortURL => ShortURLModel }
import reactivemongo.bson.BSONObjectID

import scala.concurrent.{ ExecutionContext, Future }

class ShortURLMongo @Inject() (implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi)
  extends ShortURL {

  import models.JsonFormats._

  private val logger = Logger(getClass)

  def shortUrlCollection: Future[JSONCollection] = reactiveMongoApi.database.map(_.collection("shorturl"))

  def save(entity: ShortURLModel): Future[WriteResult] = shortUrlCollection.flatMap(_.insert(entity))

  def exists(original: String): Boolean = ???

  def findById(shorted: String): Future[String] = ???
}
