package code

import org.scalatest._

import net.liftweb._
import common._
import http._
import util._
import Helpers._

trait BaseWordSpec extends WordSpec with Matchers

trait WithSessionSpec extends SuiteMixin { this: Suite =>

  protected def session = new LiftSession("", randomString(20), Empty)

  abstract override def withFixture(test: NoArgTest) = {
    S.initIfUninitted(session) { super.withFixture(test) }
  }
}

