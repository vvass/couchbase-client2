package controllers

import org.reactivecouchbase.play.{CouchbaseController}
import play.api.mvc.{Action, Controller}

class BasicController extends Controller with CouchbaseController{

  def index = Action {
    Ok("Your new application is ready.")
  }

  def echo = Action(parse.anyContent) { request =>
    Ok("Got request [" + request + "]")
  }




}
