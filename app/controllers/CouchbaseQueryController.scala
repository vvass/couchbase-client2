package controllers

import com.couchbase.client.java.CouchbaseCluster
import datasources.CouchbaseDatasourceObject
import play.api.mvc.{Action, Controller}

/**
  * Created by vvass on 6/3/16.
  */
class CouchbaseQueryController extends Controller{


  /**
    * The connection to the cluster.
    */

  def getDocument = Action(parse.anyContent) { request =>

    val results = CouchbaseDatasourceObject.findDoc()


    Ok("Got request [" + request + "] [result: " + results +"]")
  }

}
