package etl

import java.io.Writer

class PostJsonLoader(writer: Writer)
{
  def initJson() : Unit = writer.write("{\"posts\": [")
  def write(post: Post): Unit = writer.write(makePostString(post))
  def closeJson(): Unit = writer.write("]")

  private def makePostString(post: Post): String =
    s"""{"id": ${post.id}, "points": ${post.points}, "content": "${post.content}"}, """
}
