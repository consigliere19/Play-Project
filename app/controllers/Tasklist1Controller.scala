package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class Tasklist1Controller  @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
    
    def login = Action {
        Ok(views.html.login1());
    }

    def validate(username: String, password: String) = Action {
        Ok(s"$username logged in with $password");
    }

    def validatePost = Action { (request) =>
        val postVals = request.body.asFormUrlEncoded;
        postVals.map(args => {
            val username = args("username").head;
            val password = args("password").head;
            Ok(s"$username logged in with $password");
        }
        ).getOrElse(
            Ok("Oops!")
        )
    }


    def tasklist = Action {
        val tasks = List("Play Football", "Watch Football", "Sleep");
        Ok(views.html.tasklist1(tasks));
    }

}
