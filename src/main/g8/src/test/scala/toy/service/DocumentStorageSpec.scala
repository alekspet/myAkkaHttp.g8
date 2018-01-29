package toy.service

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import toy.protocol._

class DocumentStorageSpec() extends TestKit(ActorSystem("DocumentStorageSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


  "A DocumentStorage actor" must {

    "search for  document" in {

      val textStorageService = system.actorOf(DocumentStorage.props())

      textStorageService ! "1"

      expectMsg(EmptyDocument())
    }
  }
}