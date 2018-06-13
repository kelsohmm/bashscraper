package etl

import java.io.StringWriter
import org.scalatest._

object TestData {
  val Post1 = Post(id=1, points=1, content="XXX")
  val Post2 = Post(id=404040, points= -1, content="XXX")
}

class PostJsonLoaderSpec extends FlatSpec with Matchers {

  def sutLoadPosts(posts: List[Post]): String = {
    var writer = new StringWriter()
    var loader = new PostJsonLoader(writer = writer)

    loader.initJson()
    for(post <- posts) loader.write(post)
    loader.close()

    writer.toString
  }

  behavior of "PostJsonLoaderSpec"

  it should "write json object with field posts value empty list when no posts added" in {

    sutLoadPosts(List()) should equal ("""{"posts": []}""")
  }

  it should "save single post to list" in {
    sutLoadPosts(List(TestData.Post1)) should
      equal ("""{"posts": [{"id": 1, "points": 1, "content": "XXX"}]}""")
  }
  it should "separate posts with commas" in {
    sutLoadPosts(List(TestData.Post1, TestData.Post2)) should
      equal ("""{"posts": [{"id": 1, "points": 1, "content": "XXX"}, {"id": 404040, "points": -1, "content": "XXX"}]}""")
  }
}
