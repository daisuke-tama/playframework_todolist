package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db.Database
import play.api.i18n.I18nSupport
import anorm._
import anorm.SqlParser._
import scala.language.postfixOps

import models.Task

@Singleton
class HomeController @Inject()(db: Database, val controllerComponents: ControllerComponents) extends BaseController with I18nSupport {
  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.tasks)
  }

  def tasks = Action { implicit request =>
  Ok(views.html.index(all(), taskForm))
  }
  def all(): List[Task] = db.withConnection { implicit c =>
    SQL("select * from task").as(Task.task *)
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(all(), errors)),
      label => {
        db.withConnection { implicit c =>
          SQL("insert into task (label) values ({label})").on(
            'label -> label
          ).executeUpdate()
        }
        Redirect(routes.HomeController.tasks)
      }
    )
  }

  def deleteTask(id: Long) = Action {
    db.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
    Redirect(routes.HomeController.tasks)
  }
}
