package etl

import configs.Result.Failure
import net.ruippeixotog.scalascraper.browser.{Browser, JsoupBrowser}
import net.ruippeixotog.scalascraper.model.Document
import org.jsoup.HttpStatusException

import scala.util.{Failure, Success, Try}

object LatestPagesExtraction {
  def stream(start: Int, end: Int): Stream[Document] =
    getPagesUrls(start, end)
      .map{extractUrl}
      .takeWhile {_.isSuccess}
      .map{_.get}

  private def extractUrl(url: String): Try[Document] = Try(JsoupBrowser().get(url))

  private def getPagesUrls(start: Int, end: Int) =
    (start to end).toStream.map(pageNumber =>  s"http://bash.org.pl/latest/?page=$pageNumber")
}
