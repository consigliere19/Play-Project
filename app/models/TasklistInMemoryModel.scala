package models

import collection.mutable;

object TasklistInMemoryModel {
    
    private val users = mutable.Map[String, String]("Kai" -> "pass");
    private val tasks = mutable.Map[String, List[String]]("Kai" -> List("Score goal", "Win UCL", "Party"));
    
    def validateUser(username: String, password: String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false);
    }

    def createUser(username: String, password: String): Boolean = {
        ???
    }

    def getTasks(username: String): Seq[String] = {
        tasks.get(username).getOrElse(Nil);
    }

    def addTask(username: String): Unit = {
        ???
    }

    def removeTask(username: String, index: Int): Boolean = {
        ???
    }
}
