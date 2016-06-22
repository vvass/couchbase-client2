package controllers

import com.couchbase.client.java.CouchbaseCluster
import datasources.CouchbaseDatasourceObject
import play.api.mvc.{Action, Controller}

import scala.util.Try

/**
  * Created by vvass on 6/3/16.
  */
class CouchbaseQueryController extends Controller{


  /**
    * The connection to the cluster.
    */

  def getDocument = Action(parse.anyContent) { request =>

    def primaryWord = request.queryString.getOrElse("primary",null)(0).toString
    def results = CouchbaseDatasourceObject.queryDocByString(primaryWord)

    // TODO make sure primary has a check if null
    // TODO make sure primary has a check if not around

    Ok("Got request [" + request + "] [result: " + results +"]")
  }

}
