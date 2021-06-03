package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class Tasklist1Controller  @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
    
    def tasklist = Action {
        val tasks = List("Play Football", "Watch Football", "Sleep");
        Ok(views.html.tasklist1(tasks));
    }

}
