import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import etl.{ParsePosts, PostJsonLoader}
import utils.ScrapingConfig

import scala.util.Failure

object ExecuteScraping {
  val configFile = "default.config"

  def main(args: Array[String]): Unit = ScrapingConfig.fromFile(configFile) match {
      case Right(errorMsg) => println(errorMsg)
      case Left(config) =>
        val loader = PostJsonLoader.toFile(config.output)
        loader.initJson()
        val urls = getPagesUrls(config.pageStart, config.pageEnd)

        urls
          .map(url => JsoupBrowser().get(url))
          .flatMap(doc => ParsePosts.fromDoc(doc))
          .foreach(post => loader.write(post))
        loader.close()
    }

  private def getPagesUrls(start: Int, end: Int) =
    (start to end).map(pageNumber =>  s"http://bash.org.pl/latest/?page=$pageNumber")
}
