package controllers

import org.reactivecouchbase.play.{PlayCouchbase, CouchbaseController}
import play.api.libs.json.Json
import play.api.mvc.Controller

/**
  * Created by vvass on 6/2/16.
  */

case class User(name: String, surname: String, email: String)


object CouchbaseQueryController extends Controller with CouchbaseController{

  implicit val couchbaseExecutionContext = PlayCouchbase.couchbaseExecutor
  implicit val userReader = Json.reads[User]

  def getUser(key: String) = CouchbaseAction { bucket =>
    bucket.get[User](key).map { maybeUser =>
      maybeUser.map(user => Ok(views.html.user(user)).getOrElse(BadRequest(s"Unable to find user with key: $key"))
    }
  }

}
