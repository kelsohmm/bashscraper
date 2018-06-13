package utils

import java.io.File

import com.typesafe.config.ConfigException.{Missing, WrongType}
import com.typesafe.config.{Config, ConfigFactory}

case class ScrapingConfig(output: File, pageStart: Int, pageEnd: Int)

object ScrapingConfig {
  def fromFile(filename: String): Either[ScrapingConfig, String] = {
    val config = ConfigFactory.parseFile(new File(filename))
    try {
      Left(ScrapingConfig(
        output = getOutputFile(config),
        pageStart = getPageStart(config),
        pageEnd = getPageEnd(config)
      ))
    } catch {
      case e: IllegalArgumentException => Right(e.getMessage)
    }
  }

  private def getPageEnd(config: Config): Int =
    try {
      val page = config.getInt("endPage")

      if (page < getPageStart(config))
        throw new IllegalArgumentException("Field 'endPage' value must be bigger then 'startPage'.")

      page
    } catch {
      case _: Missing => throw new IllegalArgumentException("Field 'endPage' doesn't exist in config.")
      case _: WrongType => throw new IllegalArgumentException("Field 'endPage' value must be an integer.")
    }


  private def getPageStart(config: Config): Int =
    try {
      val page = config.getInt("startPage")

      if (page <= 0)
        throw new IllegalArgumentException("Field 'startPage' must contain integer > 0.")

      page
    } catch {
      case _: Missing => throw new IllegalArgumentException("Field 'startPage' doesn't exist in config.")
      case _: WrongType => throw new IllegalArgumentException("Field 'startPage' value must be an integer.")
    }

  private def getOutputFile(config: Config) =
    try {
      val filepath = config.getString("outputFilepath")
      val file = new File(filepath)

      if (!file.exists && !file.createNewFile)
        throw new IllegalArgumentException(s"Cant write to 'outputFilepath': ${file.getAbsolutePath}")

      file
    } catch {
      case _: Missing => throw new IllegalArgumentException("Field 'outputFilepath' doesn't exist in config.")
      case _: WrongType => throw new IllegalArgumentException("Field 'outputFilepath' value must be a string.")
    }
}
