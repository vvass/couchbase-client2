package datasources

import java.util.concurrent.TimeUnit

import com.couchbase.client.java.{AsyncBucket, CouchbaseCluster}
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

@Singleton
object CouchbaseDatasourceObject{

  /**
    * The instance of the client to connect to.
    */
  var cluster: CouchbaseCluster = null;

  var asyncBucket: AsyncBucket = null;

  val config: Configuration = play.api.Configuration()

  def connect() = {
    val hostname = config.getString("couchbase.bucket.host")
    val port = config.getString("couchbase.port")
    val databucket = config.getString("couchbase.bucket")
    val password = config.getString("couchbase.password")

    val uris = ArrayBuffer(URI.create("http://" + hostname + ":" + port + "/pools"))

    Try(cluster = CouchbaseCluster.create("192.168.99.100")).getOrElse(throw new ClusterCreationException)

    val bucket = cluster.openBucket("campaigns")
    asyncBucket = bucket.async()
  }

  def getInstance(): CouchbaseCluster = {
    // Create a new cluster if it doesn't exist
    if(cluster == null){
      connect()
    }
    cluster
  }

  def disconnect() = {
    if(cluster == null) (throw new ClusterVarIsNull)
    val timeout = config.getString("couchbase.shutdownTimeout").toString.toLong
    cluster.disconnect(timeout,TimeUnit.SECONDS)
  }

  def closeBucketOnly() = {
    if(cluster == null) (throw new ClusterVarIsNull)
    asyncBucket.close()
  }

  def findDoc() = {
    val results  = asyncBucket.get("1")
  }

}

