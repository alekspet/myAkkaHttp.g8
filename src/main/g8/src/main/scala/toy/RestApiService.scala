import akka.actor.ActorRef
import akka.dispatch.MessageDispatcher
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import toy.protocol._
import toy.support.JsonSupport._

/**
  * Rest service with endpoints.
  **/
class RestApiService(documentStorage : ActorRef)(implicit val timeout: Timeout, val blockingExecutor: MessageDispatcher) {

  val mainRoute: Route = getHello


  private def getHello: Route = path("get" / Segment) {
    id =>
      get {
        complete {
          (documentStorage ? id).mapTo[EmptyDocument]
        }
      }
  }
}