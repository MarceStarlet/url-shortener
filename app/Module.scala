import com.google.inject.AbstractModule
import services.{ ShortURLGenerator, ShortURLGeneratorServ => ShortURLGeneratorService }

/**
 * Dependecy Injection mapper Module
 */
class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[ShortURLGenerator]).to(classOf[ShortURLGeneratorService])
  }

}
