package datasources

import java.util.concurrent.TimeUnit

import com.couchbase.client.java.view.ViewQuery
import com.couchbase.client.java.{Bucket, AsyncBucket, CouchbaseCluster}
import com.google.inject.Singleton
import exceptions.{ClusterVarIsNull, ClusterCreationException}
import play.api.libs.json.Json
import play.api.{Play, Configuration}

import scala.collection.mutable.ArrayBuffer
import java.net.URI

import scala.util.Try


/**
  * Created by vvass on 6/3/16.
  */

object CouchbaseDatasourceObject{

  /**
    * The instance of the client to connect to.
    */
  val cluster: CouchbaseCluster = CouchbaseCluster.create("192.168.99.100")

  val bucket: Bucket = cluster.openBucket("campaigns")

  val config: Configuration = play.api.Configuration()

  def connect() = {
    val hostname = config.getString("couchbase.bucket.host")
    val port = config.getString("couchbase.port")
    val databucket = config.getString("couchbase.bucket")
    val password = config.getString("couchbase.password")

    val uris = ArrayBuffer(URI.create("http://" + hostname + ":" + port + "/pools"))
  }

  def disconnect() = {
    if(cluster == null) (throw new ClusterVarIsNull)
    val timeout = config.getString("couchbase.shutdownTimeout").toString.toLong
    cluster.disconnect(timeout,TimeUnit.SECONDS)
  }

  def closeBucketOnly() = {
    if(cluster == null) (throw new ClusterVarIsNull)
    bucket.close()
  }

  def findDoc() = {
    val results  = bucket.get("1")
    println(results.content().toString)
    results
  }

}

