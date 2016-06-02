package servlet.actor

import akka.actor.{Actor, ActorLogging}
import servlet.routes.MainServiceRoute

/**
  * Created by vvass on 5/25/16.
  */
class RouteDecisionActor extends Actor with MainServiceRoute with ActorLogging {

  override def actorRefFactory = context

  override def receive = runRoute(route)

}
