package servlet

import config.HostConfiguration
import servlet.actor.RouteDecisionActor
import spray.servlet.WebBoot
import akka.actor.{Props, ActorSystem}

/**
  * Created by vvass on 5/25/16.
  */

class Boot extends WebBoot with HostConfiguration {

  // create an actor system for application
  implicit val system = ActorSystem("simple-service")

  // create and start rest service actor
  override val serviceActor = system.actorOf(Props[RouteDecisionActor])

  system.registerOnTermination {
    // put additional cleanup code here
    system.log.info("Application shut down")
  }
}
