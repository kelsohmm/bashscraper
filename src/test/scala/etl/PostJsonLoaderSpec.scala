package etl

import java.io.StringWriter
import org.scalatest._


class PostJsonLoaderSpec extends FlatSpec with Matchers {
  behavior of "PostJsonLoaderSpec"

  var writer = new StringWriter()
  var loader = new PostJsonLoader(writer = writer)

  it should "write json object with field posts value empty list when no posts added" in {
    loader.initJson()
    loader.closeJson()
    writer.toString should equal ("{\"posts\": []")
  }
}
