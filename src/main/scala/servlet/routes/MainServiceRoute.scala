package servlet.routes

import spray.http.StatusCodes
import spray.routing.HttpService


/**
  * Created by vvass on 5/25/16.
  */


trait MainServiceRoute extends HttpService{

  implicit val executionContext = actorRefFactory.dispatcher


  def tmp = ""

  val route = {
    path("ping") {

      complete("pong")

    } ~ path("checkprim") {

      parameter('token ?) { x =>
        //      checkPrimaryWordDirective(x => complete(x))
        complete(x)
      }

    } ~ path("index" | "") {

      complete(index)

    }
  }
  
  lazy val index =
    <html>
      <body>
        <h1>Say hello to <i>spray-servlet</i>!</h1>
        <p>Defined resources:</p>
        <ul>
          <li><a href="/ping">/ping</a></li>
        </ul>
      </body>
    </html>



}