package etl

import org.scalatest._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser


class PostTransformationSpec extends FlatSpec with Matchers {

  private def getDoc(filename: String) = JsoupBrowser().parseFile(s"src/test/resources/$filename")

  behavior of "PostTransformationSpec"

  it should "parse single post" in {
    Post(
      id = 123,
      points = 321,
      content = "&lt;USR&gt; abcdefghijkmnoprstuvwxyząćęóńłźż"
    ) should equal (ParsePosts.fromDoc(getDoc("latest_single_post.html")).head)
  }
}
