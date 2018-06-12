package etl

import net.ruippeixotog.scalascraper.model.{Document, Element}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

case class Post(id: Long, points: Long, content: String)

object ParsePosts
{
  def fromDoc(doc: Document): List[Post] = doc
                                          .extract(elementList("#content .post"))
                                          .map(elem => ParsePosts.elementToPost(elem))

  private def elementToPost(postElement: Element) =
    Post(
        id = 123,
        points = 321,
        content = escapedPostContent(postElement)
    )

  private def escapedPostContent(postElement: Element) = {
    scala.xml.Utility.escape(postElement >> text(".post-content"))
  }
}