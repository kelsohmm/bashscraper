package etl

import net.ruippeixotog.scalascraper.model.{Document, Element}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

case class Post(id: Long, points: Long, content: String)

object ParsePosts
{
  def fromDoc(doc: Document): List[Post] = doc
                                          .extract(elementList("#content .post"))
                                          .map(elem => elementToPost(elem))

  private def elementToPost(postElement: Element) =
    Post(
      id = readPostId(postElement),
      points = readPostPoints(postElement),
      content = escapedPostContent(postElement)
    )

  private def escapedPostContent(postElement: Element) = {
    scala.xml.Utility.escape(postElement >> text(".post-content"))
  }

  private def readPostPoints(postElement: Element) = {
    raw"-?\d+".r
      .findFirstMatchIn(postElement >> text(".bar .points"))
      .getOrElse("")
      .toString
      .toLong
  }

  private def readPostId(postElement: Element) = {
    raw"\d+".r
      .findFirstMatchIn(postElement >> text(".bar .qid"))
      .getOrElse("")
      .toString
      .toLong
  }
}