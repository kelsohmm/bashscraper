package etl

import org.scalatest._
import net.ruippeixotog.scalascraper.browser.JsoupBrowser


class ParsePostsSpec extends FlatSpec with Matchers {

  private def getDoc(filename: String) = JsoupBrowser().parseFile(s"src/test/resources/$filename")

  behavior of "PostTransformationSpec"

  it should "parse single post" in {
    Post(
      id = 123,
      points = 321,
      content = "&lt;USR&gt; abcdefghijkmnoprstuvwxyząćęóńłźż\\\""
    ) should equal (ParsePosts.fromDoc(getDoc("latest_single_post.html")).head)
  }

  it should "parse two posts" in {
    val post1 = Post(id = 1, points = 1, content = "POST1")
    val post2 = Post(id = 2, points = -1, content = "POST2")
    ParsePosts.fromDoc(getDoc("latest_two_posts.html")) should equal (List(post1, post2))
  }

  it should "safely parse multiline content" in {
    val result = ParsePosts.fromDoc(getDoc("latest_multiline_post.html")).head
    result.content should equal ("&lt;USR1&gt; LINE1 \\n<br> &lt;USR2&gt; LINE2")
  }

  it should "ignore invalid posts" in {
    ParsePosts.fromDoc(getDoc("latest_post_invalid_id.html")) should equal (List())
    ParsePosts.fromDoc(getDoc("latest_post_invalid_points.html")) should equal (List())
    ParsePosts.fromDoc(getDoc("latest_post_missing_content.html")) should equal (List())
  }
}
