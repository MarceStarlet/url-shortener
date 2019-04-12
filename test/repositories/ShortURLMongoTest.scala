package repositories

import models.ShortURL
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class ShortURLMongoTest extends FunSuite with BeforeAndAfterEach {



  override def beforeEach() {

  }

  override def afterEach() {

  }

  test("testSave") {

    //val shortURLRepository = new ShortURLMongo()

    val shortURL = ShortURL("wwww.google.com", "", System.currentTimeMillis(), System.currentTimeMillis())

   // shortURLRepository.save(shortURL)

  }

  test("testShortUrlCollection") {

  }

  test("testFindById") {

  }

  test("testExists") {

  }

}
