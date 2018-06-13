import etl.{LatestPagesExtraction, ParsePosts, PostJsonLoader}
import utils.ScrapingConfig


object ExecuteScraping {
  val configFile = "default.config"

  def main(args: Array[String]): Unit = ScrapingConfig.fromFile(configFile) match {
      case Right(errorMsg) => println(errorMsg)
      case Left(config) =>
        val loader = PostJsonLoader.toFile(config.output)

        loader.initJson()
        try {
          LatestPagesExtraction.stream(config.pageStart, config.pageEnd)
            .flatMap(doc => ParsePosts.fromDoc(doc))
            .foreach(post => loader.write(post))
        } finally {
          loader.close()
        }
    }

}
