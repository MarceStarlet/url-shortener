import com.google.inject.AbstractModule
import services.{ ShortURLGenerator, ShortURLGeneratorServ => ShortURLGeneratorService }

class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[ShortURLGenerator]).to(classOf[ShortURLGeneratorService])
  }

}
