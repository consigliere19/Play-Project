package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.TasklistInMemoryModel

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
        postVals.map { args => 
            val username = args("username").head;
            val password = args("password").head;
            if (TasklistInMemoryModel.validateUser(username, password))
                Redirect(routes.Tasklist1Controller.tasklist());
            else
                Redirect(routes.Tasklist1Controller.login());
        }.getOrElse(
            Redirect(routes.Tasklist1Controller.login())
        )
    }


    def tasklist = Action {
        val username = "Kai";
        val tasks = TasklistInMemoryModel.getTasks(username);
        Ok(views.html.tasklist1(tasks));
    }

}
