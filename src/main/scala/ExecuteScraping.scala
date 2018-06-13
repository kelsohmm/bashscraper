import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import etl.{ParsePosts, PostJsonLoader}

object ExecuteScraping {

  def main(args: Array[String]): Unit = {
    val browser = JsoupBrowser()
    val loader = PostJsonLoader.toFile("out.json")
    loader.initJson()
    getPagesUrls(1, 5)
      .map(url => browser.get(url))
      .flatMap(doc => ParsePosts.fromDoc(doc))
      .foreach(post => loader.write(post))
    loader.close()
  }

  private def getPagesUrls(start: Int, end: Int) =
    (start to end).map(pageNumber =>  s"http://bash.org.pl/latest/?page=$pageNumber")
}
