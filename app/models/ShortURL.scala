package models

import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import play.api.libs.json._
import reactivemongo.bson.BSONObjectID

case class HttpUrl(
  original: String)

/**
 * The ShortURL model
 * @param _id
 * @param original
 * @param shorted
 * @param created
 * @param accessed
 */
case class ShortURL(
  _id: Option[BSONObjectID],
  original: String,
  shorted: String,
  created: Long,
  accessed: Long)

/**
 * The implict JSON formatter for models
 */
object JsonFormats {
  import play.api.libs.json._

  implicit val shortURLFormat: OFormat[ShortURL] = Json.format[ShortURL]

  implicit val httpShortURLFormat: OFormat[HttpUrl] = Json.format[HttpUrl]
}
