package controllers

import play.api.mvc.{Action, Controller}


class CouchbaseQueryAPI extends Controller{

  def index = Action {
    Ok("Your new application is ready.")
  }



}
