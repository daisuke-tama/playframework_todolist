package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport

import models.Task

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with I18nSupport {
  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.tasks)
  }

  def tasks = Action { implicit request =>
  Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.HomeController.tasks)
      }
    )
  }

  def deleteTask(id: Long) = TODO
}
