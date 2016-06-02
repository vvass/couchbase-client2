package servlet.directives

import shapeless._
import spray.http.{HttpHeader, Uri}
import spray.http.Uri.Path
import spray.routing._
import Directives._


/**
  * Created by vvass on 5/31/16.
  */

trait CheckPrimaryWord {

  val tokenParameter: Directive1[String] = parameter('token.as[String])
  val idParameter: Directive1[Int] = parameter('id.as[Int])

  val checkPrimaryWordDirective: Directive1[String] = {
    tokenParameter.flatMap {
      case str: String => {
        println(str)
        provide(str)
      }
      case _ => reject
    }
  }

  val checkCampaignIdDirective: Directive1[Int] = {
    idParameter.flatMap {
      case in if in > 0 => provide(in)
      case _ => reject
    }
  }
}

