package etl

import java.io.Writer

class PostJsonLoader(writer: Writer)
{
  private var firstSaved = false

  def initJson() : Unit = writer.write("{\"posts\": [")
  def closeJson(): Unit = writer.write("]}")
  def write(post: Post): Unit = {
    if(firstSaved)
      writer.write(", ")
    else
      firstSaved = true
    writer.write(makePostString(post))
  }

  private def makePostString(post: Post): String =
    s"""{"id": ${post.id}, "points": ${post.points}, "content": "${post.content}"}"""
}
