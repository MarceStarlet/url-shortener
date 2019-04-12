package models

import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import play.api.libs.json._
import reactivemongo.bson.BSONObjectID

case class HttpShortUrl(
  original: String)

case class ShortURL(
  _id: Option[BSONObjectID],
  original: String,
  shorted: String,
  created: Long,
  accessed: Long)

object JsonFormats {
  import play.api.libs.json._

  implicit val shortURLFormat: OFormat[ShortURL] = Json.format[ShortURL]

  implicit val httpShortURLFormat: OFormat[HttpShortUrl] = Json.format[HttpShortUrl]
}

//object ShortURLSerializer {
//
//  implicit object ShortURLWriter extends BSONDocumentWriter[ShortURL] {
//
//    def write(shortURL: ShortURL): BSONDocument = BSONDocument(
//      "_id" -> BSONObjectID.generate(),
//      "original" -> shortURL.original,
//      "shorted" -> shortURL.shorted,
//      "created" -> shortURL.created,
//      "accessed" -> shortURL.accessed)
//  }
//
//}
