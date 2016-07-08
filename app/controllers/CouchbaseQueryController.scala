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

    /* We want to create a list of of words inside of the request so that we can
    ** query each one individually.
    */
    def requestList = request.queryString.getOrElse("primary",null)(0).toString.split(" +")

    /* We don't want to parse words that are less then 3 letters long. It is useless.
    ** This is where we parse out the string from the request in order to send it
    ** to the query for couchbase
    */
    val results = new StringBuilder
    for (word <- requestList if word.length >= 3)
      results ++= CouchbaseDatasourceObject.queryDocByString(word.replaceAll("\"","")).toString

//    def results = CouchbaseDatasourceObject.queryDocByString(primaryWord)

    // TODO make sure primary has a check if null
    // TODO make sure primary has a check if not around

    Ok("result: " + results)
  }

}
