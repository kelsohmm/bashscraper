package etl

import net.ruippeixotog.scalascraper.model.{Document, Element}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._


object ParsePosts
{
  def fromDoc(doc: Document): List[Post] = {
    doc
      .extract(elementList("#content .post"))
      .flatMap(elem => elementToPost(elem))
  }


  private def elementToPost(postElement: Element) =
    try{
      Some(Post(
        id = readPostId(postElement),
        points = readPostPoints(postElement),
        content = escapedPostContent(postElement)
      ))
    }
    catch{
      case _: IllegalArgumentException => None
      case _: NoSuchElementException => None
    }

  private def escapedPostContent(postElement: Element) = {
    (postElement >> element(".post-content"))
      .innerHtml
      .replace("\n", "\\n")
      .replace("\"", "\\\"")
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