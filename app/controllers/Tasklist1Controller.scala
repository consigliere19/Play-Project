package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.TasklistInMemoryModel

@Singleton
class Tasklist1Controller  @Inject()(val controllerComponents: ControllerComponents) extends BaseController{
    
    def login = Action { implicit request =>
        Ok(views.html.login1());
    }

    def logout = Action {
        Redirect(routes.Tasklist1Controller.login()).withNewSession;
    }

    def validate(username: String, password: String) = Action {
        Ok(s"$username logged in with $password");
    }

    def validatePost = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded;
        postVals.map { args => 
            val username = args("username").head;
            val password = args("password").head;
            if (TasklistInMemoryModel.validateUser(username, password))
                Redirect(routes.Tasklist1Controller.tasklist()).withSession("username" -> username);
            else
                Redirect(routes.Tasklist1Controller.login()).flashing("error" -> "Invalid username or password.");
        }.getOrElse(
            Redirect(routes.Tasklist1Controller.login())
        )
    }

    def createUser = Action { implicit request =>
        val postVals = request.body.asFormUrlEncoded;
        postVals.map { args => 
            val username = args("username").head;
            val password = args("password").head;
            if (TasklistInMemoryModel.createUser(username, password))
                Redirect(routes.Tasklist1Controller.tasklist()).withSession("username" -> username);
            else
                Redirect(routes.Tasklist1Controller.login()).flashing("error" -> "Failed to create new user.");
        }.getOrElse(
            Redirect(routes.Tasklist1Controller.login())
        )
    }


    def tasklist = Action { implicit request => 
        val usernameOption = request.session.get("username");
        usernameOption.map { username => 
            val tasks = TasklistInMemoryModel.getTasks(username);
            Ok(views.html.tasklist1(tasks));
        }.getOrElse(Redirect(routes.Tasklist1Controller.login()));        
    }

    def addTask = Action { implicit request =>
        val usernameOption = request.session.get("username");
        usernameOption.map { username =>
            val postVals = request.body.asFormUrlEncoded;
            postVals.map { args => 
                val task = args("newTask").head;
                TasklistInMemoryModel.addTask(username, task);
                Redirect(routes.Tasklist1Controller.tasklist())
            }.getOrElse(
                Redirect(routes.Tasklist1Controller.tasklist())
            )
        }.getOrElse(
            Redirect(routes.Tasklist1Controller.login())
        )
    }

    def deleteTask = Action { implicit request =>
        val usernameOption = request.session.get("username");
        usernameOption.map { username =>
            val postVals = request.body.asFormUrlEncoded;
            postVals.map { args => 
                val index = args("index").head.toInt;
                TasklistInMemoryModel.removeTask(username, index);
                Redirect(routes.Tasklist1Controller.tasklist())
            }.getOrElse(
                Redirect(routes.Tasklist1Controller.tasklist())
            )
        }.getOrElse(
            Redirect(routes.Tasklist1Controller.login())
        )
    }

}
