package toy

import akka.actor.ActorRef
import akka.dispatch.MessageDispatcher
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.Timeout
import org.scalatest.{Matchers, WordSpec}
import toy.service.DocumentStorage

import scala.concurrent.duration._

class RestApiServiceSpec extends WordSpec with Matchers with ScalatestRouteTest {

  private implicit val timeout: Timeout = 5.seconds
  private implicit val dispatcher: MessageDispatcher = system.dispatchers.defaultGlobalDispatcher

  private val documentStorage : ActorRef = system.actorOf(DocumentStorage.props())
  private val restService = new RestApiService(documentStorage)

  private val route = restService.mainRoute

  "The RestApiService" should {

    "return hello to id passed in uri" in {
      Get("/get/1") ~> route ~> check {
        responseAs[String] shouldEqual "{}"
      }
    }
  }
}