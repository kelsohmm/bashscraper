package etl

import java.io.{File, PrintWriter, Writer}

class PostJsonLoader(writer: Writer)
{
  private var firstSaved = false

  def initJson() : Unit = writer.write("{\"posts\": [")
  def close(): Unit = {
    writer.write("]}")
    writer.close()
  }
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

object PostJsonLoader {
  def toFile(file: File): PostJsonLoader = {
    new PostJsonLoader(new PrintWriter(file))
  }
}
