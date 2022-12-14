package models

import anorm._
import anorm.SqlParser._

case class Task(id: Long, label: String)

object Task {
  val task = {
    get[Long]("id") ~ 
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }  
}
